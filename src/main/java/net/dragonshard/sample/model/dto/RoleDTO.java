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

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.core.annotation.Sensitive;
import net.dragonshard.dsf.core.enums.SensitiveTypeEnum;
import net.dragonshard.dsf.web.core.framework.model.BaseModel;

/**
 * <p>
 * 角色DTO
 * </p>
 *
 * @author dragonshard
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends BaseModel {

  private static final long serialVersionUID = 1L;

  /**
   * SensitiveTypeEnum.CUSTOMER 自定义脱敏规则 prefixNoMaskLen 前置不需要打码的长度 suffixNoMaskLen 后置不需要打码的长度
   */
  @Sensitive(type = SensitiveTypeEnum.CUSTOMER, prefixNoMaskLen = 2, suffixNoMaskLen = 4)
  @ApiModelProperty(notes = "角色名称")
  private String roleName;

  // 使用了默认实现的 SensitiveTypeEnum.CHINESE_NAME 脱敏规则
  @Sensitive(type = SensitiveTypeEnum.CHINESE_NAME)
  @ApiModelProperty(notes = "备注")
  private String remark;

  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(notes = "修改时间")
  private LocalDateTime modifiedTime;

}
