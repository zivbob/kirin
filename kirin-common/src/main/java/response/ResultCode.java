package response;

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

    /** 系统错误 */
    SYS_ERROR("402", "系统错误"),

    /**
     * 未知的错误
     */
    UNKNOWN_ERROR("1000", "未知错误");

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
