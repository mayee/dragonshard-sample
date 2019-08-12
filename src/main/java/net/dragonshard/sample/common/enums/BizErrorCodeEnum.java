/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.dragonshard.sample.common.enums;


import net.dragonshard.dsf.core.exception.UnknownEnumException;
import net.dragonshard.dsf.web.core.framework.model.BizErrorCode;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务异常枚举
 *
 * @author mayee
 * @date 2019-06-06
 *
 * @version v1.0
 **/
public enum BizErrorCodeEnum {

    //----------------------------------------------------业务异常----------------------------------------------------
    /**
     * 用户名密码错误
     */
    USERNAME_OR_PASSWORD_IS_WRONG(HttpServletResponse.SC_BAD_REQUEST, true, "用户名密码错误"),
    /**
     * 用户被禁用
     */
    USER_IS_DISABLED(HttpServletResponse.SC_NOT_ACCEPTABLE, true, "用户被禁用"),
    /**
     * 数据不存在
     */
    DATA_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, true, "数据不存在"),
    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(HttpServletResponse.SC_BAD_REQUEST, true, "用户名已存在"),

    ;

    private final int httpCode;
    private final boolean show;
    private final String msg;

    BizErrorCodeEnum(int httpCode, boolean show, String msg) {
        this.httpCode = httpCode;
        this.msg = msg;
        this.show = show;
    }

    /**
     * 转换为ErrorCode(自定义返回消息)
     *
     * @param msg
     * @return
     */
    public BizErrorCode convert(String msg) {
        return BizErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg).build();
    }

    /**
     * 转换为ErrorCode
     *
     * @return
     */
    public BizErrorCode convert() {
        return BizErrorCode.builder().httpCode(httpCode()).show(show()).error(name()).msg(msg()).build();
    }

    public static BizErrorCodeEnum getErrorCode(String errorCode) {
        BizErrorCodeEnum[] enums = BizErrorCodeEnum.values();
        for (BizErrorCodeEnum errorCodeEnum : enums) {
            if (errorCodeEnum.name().equalsIgnoreCase(errorCode)) {
                return errorCodeEnum;
            }
        }
        throw new UnknownEnumException("Error: Unknown errorCode, or do not support changing errorCode!\n");
    }

    public int httpCode() {
        return this.httpCode;
    }

    public String msg() {
        return this.msg;
    }

    public boolean show() {
        return this.show;
    }

}
