package response;

import lombok.Getter;

/**
 * 公共请求响应结果
 *
 * @author ziv
 * @date 2019-08-05
 */
@Getter
public class JsonResult<T> {

    /**
     * 应答码
     */
    private String code;

    /**
     * 应答消息
     */
    private String msg;

    /**
     * 应答数据
     */
    private T data;

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public JsonResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public static JsonResult success() {
        return new JsonResult(ResultCode.SUCCESS);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(ResultCode.SUCCESS, data);
    }

    public static JsonResult error() {
        return new JsonResult(ResultCode.SYS_ERROR);
    }

    public static JsonResult error(String errorMsg) {
        return new JsonResult(ResultCode.SYS_ERROR, errorMsg);
    }


}
