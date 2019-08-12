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
package net.dragonshard.sample.service;

import net.dragonshard.dsf.data.mybatis.framework.service.IDsfService;
import net.dragonshard.sample.enums.StatusEnum;
import net.dragonshard.sample.model.entity.User;

/**
 * <p>
 * User 服务类
 * </p>
 *
 * @author dragonshard
 */
public interface IUserService extends IDsfService<User> {

    /**
     * @param id        用户id
     * @param status    新状态的枚举值
     */
    void updateStatus(Long id, StatusEnum status);

    /**
     * 创建用户，设置默认密码，状态设置为不可用
     *
     * @param user  用户
     */
    void saveUser(User user);

}
