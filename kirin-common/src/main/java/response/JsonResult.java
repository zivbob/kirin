package response;

/**
 * 公共请求响应结果
 *
 * @author ziv
 * @date 2019-08-05
 */
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
    private String data;
}
