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
package net.dragonshard.sample.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.core.toolkit.EncryptUtils;
import net.dragonshard.dsf.data.mybatis.framework.service.impl.DsfServiceImpl;
import net.dragonshard.dsf.dynamic.datasource.annotation.DsfAssignDataSource;
import net.dragonshard.sample.common.ApiAssert;
import net.dragonshard.sample.common.enums.BizErrorCodeEnum;
import net.dragonshard.sample.enums.StatusEnum;
import net.dragonshard.sample.mapper.UserMapper;
import net.dragonshard.sample.model.entity.User;
import net.dragonshard.sample.service.IUserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * IUserService 服务实现类
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@Service
public class UserServiceImpl extends DsfServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void updateStatus(Long id, StatusEnum status) {
        User user = getById(id);
        ApiAssert.notNull(BizErrorCodeEnum.DATA_NOT_FOUND, user);
        user = new User();
        user.setId(id);
        user.setStatus(status);
        updateById(user);
    }

    @Override
    public void saveUser(User user) {
        //设置默认密码，算法SHA-256，最终16进制结果拼接为：密文+盐
        byte[] salt = EncryptUtils.generateSalt(16);
        byte[] pwd = EncryptUtils.sha256(user.getLoginName().getBytes(), salt);
        user.setPassword(Hex.encodeHexString(pwd) + Hex.encodeHexString(salt));
        //默认禁用
        user.setStatus(StatusEnum.DISABLE);
        save(user);
    }

    @DsfAssignDataSource("custom_2")
    @Override
    public User getById(Serializable id){
        // 从 custom_2 中读取数据
        User userCustom2 = getBaseMapper().selectById(id);
        log.info("userCustom2 > {}", userCustom2);

        return userCustom2;
    }

}
