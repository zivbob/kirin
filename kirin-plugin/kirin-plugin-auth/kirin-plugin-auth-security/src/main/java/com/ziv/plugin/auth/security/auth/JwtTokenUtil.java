package com.ziv.plugin.auth.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * @author ziv
 * @date 2019-03-07
 */
@Slf4j
@Component
public class JwtTokenUtil {

    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";

    /**
     * token加密secret
     */
    private String secret = "eSnoZWg1pj9CF4kV";

    /**
     * 生成token方法
     * @param claims token内容
     * @return String token字符串
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中获取jwt负载
     * @param token token串
     * @return Claims jwt负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("token解析失败");
            log.error(e.getMessage());
        }
        return claims;
    }

    /**
     * 从token中获取用户名
     * @param token token
     * @return 用户名
     */
    public String getUserNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        } else {
            return null;
        }
    }

    /**
     * 生成token
     * @param userDetails 用户
     * @return String token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }
}
