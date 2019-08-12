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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dragonshard.dsf.data.mybatis.framework.service.impl.DsfServiceImpl;
import net.dragonshard.dsf.dynamic.datasource.annotation.DsfAssignDataSource;
import net.dragonshard.sample.mapper.RoleMapper;
import net.dragonshard.sample.model.entity.Role;
import net.dragonshard.sample.model.entity.User;
import net.dragonshard.sample.service.IRoleService;
import net.dragonshard.sample.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * IRoleService 服务实现类
 * </p>
 *
 * @author dragonshard
 */
@Slf4j
@AllArgsConstructor
@Service
public class RoleServiceImpl extends DsfServiceImpl<RoleMapper, Role> implements IRoleService {

    private IUserService userService;

    @DsfAssignDataSource("custom")
    @Override
    public Role getByIdWithLoadBalance(Long id) {
        // 每次请求数据会从 custom_1 和 custom_2 中轮流读取。
        return getBaseMapper().selectById(id);
    }

    /**
     * 用于演示的方法
     * 完整步骤：
     * 1-切换到 custom_1，获取role数据；
     * 2-切换到 custom_2，获取user数据；
     * 3-最终返回 custom_2 的 user 的 loginName；
     *
     * @param id
     * @return
     */
    @DsfAssignDataSource("custom_1")
    @Override
    public String getByIdWithNestedSwitch(Long id) {
        Role roleCustom1 = getBaseMapper().selectById(id);
        log.info("roleCustom1 > {}", roleCustom1);

        // 从 custom_2 的 user 中读取数据
        User userCustom2 = userService.getById(id);

        // 最终返回的是 custom_2 的 user 的 loginName
        return userCustom2.getLoginName();
    }


}
