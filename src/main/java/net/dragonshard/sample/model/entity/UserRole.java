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
package net.dragonshard.sample.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.web.core.framework.model.BaseModel;


/**
 * <p>
 * UserRole
 * </p>
 *
 * @author dragonshard
 */
@TableName("sys_user_role")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "用户ID")
    private Long uid;

    @ApiModelProperty(notes = "角色ID")
    private Long roleId;

}
