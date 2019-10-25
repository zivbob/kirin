package com.ziv.plugin.auth.shiro.auth;

import com.ziv.common.token.AuthorisationInfo;
import com.ziv.common.token.InvalidateTokenException;
import com.ziv.common.token.JwtUserInfo;
import com.ziv.common.token.JwtUtils;
import com.ziv.plugin.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private RedisUtils redisUtils;

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
        log.info("权限验证");
        AuthorisationInfo authInfo = (AuthorisationInfo) principalCollection.getPrimaryPrincipal();
        // 获取用户权限
        Set<String> permsSet = authInfo.getUserInfo().getPermissions();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
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
        log.info("身份认证");
        String token = (String) authenticationToken.getPrincipal();
        // 解析token获取用户信息
        try {
            // 验证token
            JwtUtils.parseJwt(token);
            // 从redis获取token
            AuthorisationInfo authInfo = redisUtils.get(token, AuthorisationInfo.class);
            if (authInfo == null) {
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }
            // 更新token有效期
            redisUtils.setExpire(token);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(authInfo, token, getName());
            return info;
        } catch (InvalidateTokenException e) {
            e.printStackTrace();
            throw new AuthenticationException(e.getMessage());
        }
    }
}