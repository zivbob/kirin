package com.ziv.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken((HttpServletRequest) request);
        /*if (token == null || "".equals(token)) {
            return null;
        }*/
        log.info("createToken:" + token);
        return new AuthToken(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // TODO token验证
        String token = getRequestToken((HttpServletRequest)request);
        log.info("token" + token);
        if (token == null && "".equals(token)) {
            return false;
        } else {
            return executeLogin(request, response);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.info("没权限");
        try {
            //处理登录失败的异常
            log.info("异常");
            log.info(e.getMessage());
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            response.getWriter().print("false");
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
