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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.data.mybatis.framework.controller.MyBatisController;
import net.dragonshard.dsf.data.secret.annotation.SecretBody;
import net.dragonshard.dsf.limit.annotation.Limit;
import net.dragonshard.dsf.web.core.bean.Result;
import net.dragonshard.dsf.web.core.mapping.ApiVersion;
import net.dragonshard.sample.common.ApiAssert;
import net.dragonshard.sample.common.enums.BizErrorCodeEnum;
import net.dragonshard.sample.model.bo.RoleBO;
import net.dragonshard.sample.model.dto.RoleDTO;
import net.dragonshard.sample.model.entity.Role;
import net.dragonshard.sample.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Role 前端控制器
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@Api(tags = {"Role"}, description = "角色表相关接口")
@RestController
@ApiVersion(1)
@RequestMapping(value = "/api/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
@AllArgsConstructor
public class RoleController extends MyBatisController {

    private IRoleService roleService;

    @ApiOperation(value = "查询所有角色(分页)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleName", value = "需要查询的角色名", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<Result<IPage<RoleDTO>>> page(@RequestParam(value = "roleName", required = false) String roleName) {
        return success(
            roleService.lambdaQuery()
                .likeRight(StringUtils.isNotEmpty(roleName), Role::getRoleName, roleName)
                .orderByAsc(Role::getRemark).orderByDesc(Role::getCreateTime)
                .page(this.getPage())
                .convert(e -> e.convert(RoleDTO.class))
        );
    }

    @SecretBody(value = "aes", ciphertextType = "base64")
    @ApiOperation("创建角色")
    @PostMapping
    public ResponseEntity insert(@RequestBody @Validated(RoleBO.Create.class) RoleBO roleBO) throws Exception {
        roleService.save(roleBO.convert(Role.class));
        return success(HttpStatus.CREATED);
    }

    @ApiOperation("查询单个角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    // 当使用本地限流时，limitPeriod 必须设置为1
    @Limit(name = "limitLocalName", key = "limitLocalKey", limitPeriod = 1, limitCount = 1)
    @GetMapping("/{id}")
    public ResponseEntity<Result<RoleDTO>> get(@PathVariable("id") Long id) {
        Role role = roleService.getById(id);
        ApiAssert.notNull(BizErrorCodeEnum.DATA_NOT_FOUND, role);
        return success(role.convert(RoleDTO.class));
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Validated(RoleBO.Update.class) RoleBO roleBO) {
        Role role = roleBO.convert(Role.class);
        role.setId(id);
        roleService.updateById(role);
        return success();
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return success(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("默认负载均衡模式的数据读取")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @GetMapping("/LB/{id}")
    public ResponseEntity<Result<RoleDTO>> getWithLoadBalance(@PathVariable("id") Long id) {
        Role role = roleService.getByIdWithLoadBalance(id);
        return success(role.convert(RoleDTO.class));
    }

    @ApiOperation("数据源嵌套切换")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @GetMapping("/NS/{id}")
    public ResponseEntity<Result<String>> getByIdWithNestedSwitch(@PathVariable("id") Long id) {
        String loginName = roleService.getByIdWithNestedSwitch(id);
        return success(loginName);
    }

}
