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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dragonshard.sample.mapper.UserRoleMapper;
import net.dragonshard.sample.model.entity.UserRole;
import net.dragonshard.sample.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * // * <p> IUserRoleService 服务实现类
 * </p>
 *
 * @author dragonshard
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements
  IUserRoleService {

}
