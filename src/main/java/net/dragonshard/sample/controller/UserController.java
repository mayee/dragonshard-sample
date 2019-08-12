/*
 *   Copyright 1999-2018 dragonshard.net.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package net.dragonshard.sample.controller;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.data.mybatis.framework.controller.MyBatisController;
import net.dragonshard.dsf.data.redis.service.CacheService;
import net.dragonshard.dsf.data.secret.annotation.SecretBody;
import net.dragonshard.dsf.web.core.bean.Result;
import net.dragonshard.dsf.web.core.mapping.ApiVersion;
import net.dragonshard.sample.common.ApiAssert;
import net.dragonshard.sample.common.enums.BizErrorCodeEnum;
import net.dragonshard.sample.enums.StatusEnum;
import net.dragonshard.sample.model.bo.UserBO;
import net.dragonshard.sample.model.dto.UserDTO;
import net.dragonshard.sample.model.entity.User;
import net.dragonshard.sample.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * User 前端控制器
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@Api(tags = {"User"}, description = "系统用户表相关接口")
@RestController
@ApiVersion(1)
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserController extends MyBatisController {

    @Value("${dragonshard.redis.enabled:false}")
    private String redisIsEnabled;
    @Autowired
    private IUserService userService;

    @ApiOperation("查询所有用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "loginName", value = "需要检查的账号", paramType = "query"),
        @ApiImplicitParam(name = "nickname", value = "需要检查的账号", paramType = "query"),
        @ApiImplicitParam(name = "status", value = "需要检查的账号", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<Result<IPage<UserDTO>>> page(@RequestParam(value = "loginName", required = false) String loginName,
                                                       @RequestParam(value = "nickname", required = false) String nickname,
                                                       @RequestParam(value = "status", required = false) StatusEnum status) {
        return success(
            userService.lambdaQuery().likeRight(StringUtils.isNotEmpty(loginName), User::getLoginName, loginName)
                .likeRight(StringUtils.isNotEmpty(nickname), User::getNickname, nickname)
                .eq(Objects.nonNull(status), User::getStatus, status)
                .page(this.<User>getPage())
                .convert(e -> e.convert(UserDTO.class))
        );
    }

    /**
     * RESTful优化后的使用方法
     *
     * @param id userId
     * @return userDTO
     */
    @ApiOperation("查询单个用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @GetMapping(value = "/{id}", name = "pathVariableDemo")
    public ResponseEntity<Result<UserDTO>> get(@PathVariable("id") Long id) {
        User user;

        // 从缓存读取
        if (Boolean.valueOf(redisIsEnabled) && CacheService.hasKey(id.toString())) {
            user = CacheService.get(id.toString(), new TypeReference<User>() {
            });
        } else {
            user = userService.getById(id);
            // 放入缓存
            if (Boolean.valueOf(redisIsEnabled)) {
                CacheService.save(id.toString(), user, 60);
            }
        }

        ApiAssert.notNull(BizErrorCodeEnum.DATA_NOT_FOUND, user);
        UserDTO userDTO = user.convert(UserDTO.class);
        return success(userDTO);
    }

    @ApiOperation("设置用户状态")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity updateStatus(@PathVariable("id") Long id, @RequestBody @Validated(UserBO.Status.class) UserBO userBO) {
        userService.updateStatus(id, userBO.getStatus());
        return success();
    }

    @ApiOperation("创建用户")
    @SecretBody(value = "rsa", ciphertextType = "hex", providerClass = BouncyCastleProvider.class)
    @PostMapping
    public ResponseEntity create(@RequestBody @Validated(UserBO.Create.class) UserBO userBO) {
        int count = userService.lambdaQuery().eq(User::getLoginName, userBO.getLoginName()).count();
        ApiAssert.isTrue(BizErrorCodeEnum.USERNAME_ALREADY_EXISTS, count == 0);
        User user = userBO.convert(User.class);
        userService.saveUser(user);
        return success(HttpStatus.CREATED);
    }

    @ApiOperation("修改用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Validated(UserBO.Update.class) UserBO userBO) {
        User user = userBO.convert(User.class);
        user.setId(id);
        userService.updateById(user);
        return success();
    }
}
