package com.ziv.plugin.auth.shiro.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 权限token类
 *
 * @author ziv
 * @date 2019-08-09
 */
public class AuthToken implements AuthenticationToken {
    private String token;

    public AuthToken (String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
