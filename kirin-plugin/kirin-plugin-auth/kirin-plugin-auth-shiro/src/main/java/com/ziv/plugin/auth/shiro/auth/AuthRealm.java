package com.ziv.plugin.auth.shiro.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 授权域
 *
 * @author ziv
 * @date 2019-08-12
 */
@Slf4j
@Component
public class AuthRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 权限验证（验证权限时调用）
     * @param principalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入权限验证");
        AuthInfo authInfo = (AuthInfo) principalCollection.getPrimaryPrincipal();
        log.info("token:" + authInfo.getToken());
        Set<String> permsSet = authInfo.getPermsSet();
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 身份认证（登录时调用）
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入身份验证");
        String token = (String) authenticationToken.getPrincipal();
        if (token == null) {
            throw new IncorrectCredentialsException("缺少token");
        }

        if (!token.equals("123456")) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        AuthInfo authInfo = new AuthInfo();
        Set<String> permsSet = new HashSet<>();
        permsSet.add("admin");
        permsSet.add("test");
        permsSet.add("user:teach");
        authInfo.setPermsSet(permsSet);
        authInfo.setToken(token);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(authInfo, token, getName());
        return info;
    }
}