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
package net.dragonshard.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.web.core.framework.converter.Convert;
import net.dragonshard.sample.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户DTO
 * </p>
 *
 * @author dragonshard
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "主键")
    private Long id;

    @ApiModelProperty(notes = "登陆名")
    private String loginName;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;

    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(notes = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(notes = "用户角色ID")
    private List<Long> roleIds;
}
