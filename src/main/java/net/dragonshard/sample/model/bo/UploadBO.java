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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dragonshard.dsf.web.core.framework.converter.Convert;
import net.dragonshard.sample.enums.StatusEnum;
import net.dragonshard.sample.model.bo.UserBO.Status;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 上传BO
 * </p>
 *
 * @author dragonshard
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UploadBO extends Convert {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(notes = "是否覆盖 0.否 1.是")
  @NotNull(message = "是否覆盖不能为空")
  private Integer isCover;

  @ApiModelProperty(notes = "要存储的文件名称")
  @NotBlank(message = "文件名称不能为空")
  @Length(max = 255, message = "长度不能超过255个字符")
  private String fileName;

  @ApiModelProperty(notes = "签名")
  private String token;

  @ApiModelProperty(notes = "时间戳")
  private Long timestamp;

}
