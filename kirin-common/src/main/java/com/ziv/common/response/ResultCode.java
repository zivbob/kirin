package com.ziv.common.response;

/**
 * 公共响应状态枚举类
 *
 * @author ziv
 * @date 2019-08-05
 */
public enum  ResultCode {

    /**
     * 成功
     */
    SUCCESS("200", "成功"),

    /**
     * 系统异常
     */
    SYS_ERROR("500", "系统错误"),

    /**
     * 登录异常
     */
    USER_NOT_EXIST("401", "用户不存在或账号名密码不对"),

    /**
     * 未知的错误
     */
    UNKNOWN_ERROR("9999", "未知错误");

    private String code;

    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
