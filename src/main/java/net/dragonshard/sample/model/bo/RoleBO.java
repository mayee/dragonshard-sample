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
package net.dragonshard.sample.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.web.core.framework.converter.Convert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 角色BO
 * </p>
 *
 * @author dragonshard
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleBO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "角色名称")
    @NotBlank(groups = {Create.class, Update.class}, message = "角色名称不能为空")
    @Length(max = 64, groups = {Create.class, Update.class}, message = "长度不能超过64个字符")
    private String roleName;

    @ApiModelProperty(notes = "备注")
    @Length(max = 128, groups = {Create.class, Update.class}, message = "长度不能超过128个字符")
    private String remark;


    public interface Create {

    }

    public interface Update {

    }

}
