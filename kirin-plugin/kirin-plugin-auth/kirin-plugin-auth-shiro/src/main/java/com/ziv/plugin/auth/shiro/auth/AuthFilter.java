package com.ziv.plugin.auth.shiro.auth;

import com.alibaba.fastjson.JSON;
import com.ziv.common.response.JsonResult;
import com.ziv.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限过滤器
 *
 * @author ziv
 * @date 2019-08-09
 */
@Slf4j
public class AuthFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        log.info("创建token");
        String token = getRequestToken((HttpServletRequest) request);
        if (token == null || "".equals(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    /**
     * 访问受限处理
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("请求受限");
        String token = getRequestToken((HttpServletRequest)request);
        // token为空直接请求失败
        if (token == null || token.isEmpty()) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            JsonResult result = JsonResult.error(ResultCode.TOKEN_INVALIDATE);
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.getWriter().print(JSON.toJSONString(result));
            return false;
        } else {
            return executeLogin(request, response);
        }
    }

    /**
     * 登录失败处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            JsonResult result =JsonResult.error(ResultCode.TOKEN_INVALIDATE, throwable.getMessage());
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e1) {
            log.error(e1.getMessage());
        }
        return false;
    }

    private String getRequestToken(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(token == null || "".equals(token)){
            token = request.getParameter("token");
        }
        return token;
    }
}
