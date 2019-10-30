package com.ziv.plugin.auth.security.filter;

import com.alibaba.fastjson.JSON;
import com.ziv.common.token.AuthorisationInfo;
import com.ziv.common.token.InvalidateTokenException;
import com.ziv.common.token.JwtUtils;
import com.ziv.plugin.auth.security.auth.MyUserDetails;
import com.ziv.plugin.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token拦截器
 * @author ziv
 * @date 2019-04017
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    /**
     * jwk在redis中的key值
     */
    private static final String JWK_IN_REDIS = "jwk";

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            try {
                // TODO 系统集成网关的时候token验证迁移到网关过滤器
                String jwkStr = redisUtils.get(JWK_IN_REDIS);
                RsaJsonWebKey rsaJsonWebKey = new RsaJsonWebKey(JSON.parseObject(jwkStr));
                // 验证token
                JwtUtils.parseJwt(token, rsaJsonWebKey);
                // 从redis获取用户信息
                AuthorisationInfo authorisationInfo = redisUtils.get(token, AuthorisationInfo.class);
                if (authorisationInfo != null) {
                    MyUserDetails userDetails = new MyUserDetails(authorisationInfo.getUserInfo().getUserName(),
                            null, authorisationInfo.getUserInfo().getPermissions());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // 更新redis中token有效期
                    redisUtils.setExpire(token);
                } else {
                    throw new BadCredentialsException("无效token");
                }
            } catch (InvalidateTokenException | JoseException e) {
                e.printStackTrace();
                throw new BadCredentialsException(e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
