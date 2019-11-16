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
package net.dragonshard.sample.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import net.dragonshard.dsf.web.core.enums.IEnum;

/**
 * <p>
 * 状态枚举
 * </p>
 *
 * @author Caratacus
 */
public enum StatusEnum implements IEnum {

  /**
   * 正常
   */
  NORMAL(1),
  /**
   * 不可用
   */
  DISABLE(0);

  @EnumValue
  private final int value;

  StatusEnum(final int value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public int getValue() {
    return this.value;
  }
}
