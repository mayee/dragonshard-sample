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
import net.dragonshard.sample.enums.StatusEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 用户BO
 * </p>
 *
 * @author dragonshard
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserBO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "登陆名")
    @NotBlank(groups = {Create.class, Update.class}, message = "用户名不能为空")
    @Length(max = 16, groups = {Create.class, Update.class}, message = "长度不能超过16个字符")
    private String loginName;

    @ApiModelProperty(notes = "昵称")
    @NotBlank(groups = {Create.class, Update.class}, message = "昵称不能为空")
    @Length(max = 50, groups = {Create.class, Update.class}, message = "长度不能超过50个字符")
    private String nickname;

    @Email(groups = {Create.class, Update.class}, message = "邮箱格式不正确")
    @ApiModelProperty(notes = "邮箱")
    @Length(max = 100, groups = {Create.class, Update.class}, message = "长度不能超过100个字符")
    private String email;

    @NotNull(groups = Status.class, message = "用户状态不能为空")
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;

    @ApiModelProperty(notes = "用户角色ID")
    @NotEmpty(groups = {Create.class, Update.class}, message = "用户角色不能为空")
    private List<Long> roleIds;

    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
