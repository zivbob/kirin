package com.ziv.plugin.exception;

import com.ziv.common.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author ziv
 * @date 2109-10-15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error("getMethod" + request.getMethod());
        log.error("请求url：" + request.getRequestURL());
        log.error("请求资源：" + request.getRequestURI());
        log.error("请求参数：" + request.getQueryString());
        return JsonResult.error(e.getMessage());
    }
}
