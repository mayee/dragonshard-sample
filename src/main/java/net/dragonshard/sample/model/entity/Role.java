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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.web.core.framework.model.BaseModel;


/**
 * <p>
 * Role
 * </p>
 *
 * @author dragonshard
 */
@TableName("sys_role")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseModel {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(notes = "角色名称")
  private String roleName;

  @TableField(value = "create_time", fill = FieldFill.INSERT)
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createTime;

  @TableField(value = "modified_time", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(notes = "修改时间")
  private LocalDateTime modifiedTime;

  @ApiModelProperty(notes = "备注")
  private String remark;

}
