package com.ziv.common.token;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;

/**
 * jwtToken工具类
 *
 * @author ziv
 * @date 2019-10-12
 */
public class JwtUtils {
    /**
     * token加解密秘钥
     */
    private static final String SECRET = "3n8scXQMZVObuUN6l#e^4XPgJKt#3I@#";

    public static <T> String generatorToken(T t) throws JOSEException {
        // 创建头部
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // 创建负载
        JWSObject jwsObject = new JWSObject(header, new Payload(JSON.toJSONString(t)));
        // 生成token
        jwsObject.sign(new MACSigner(SECRET.getBytes()));
        return jwsObject.serialize();
    }

    public static <T> T getFromToken(String token, Class<T> cla) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        // 建立解锁密钥
        JWSVerifier jwsVerifier = new MACVerifier(SECRET);
        // 验证token正确性
        if(jwsObject.verify(jwsVerifier)) {
            // 获取核载
            String payLoad = jwsObject.getPayload().toString();
            if (payLoad != null && !payLoad.isEmpty()) {
                return JSON.parseObject(payLoad, cla);
            } else {
                throw new JOSEException("负载为空");
            }
        } else {
            throw new JOSEException("token无效");
        }
    }

    public static void main1(String[] args) throws JOSEException {
        JwtUserInfo userInfo = new JwtUserInfo();
        userInfo.setUserKey("123456");
        userInfo.setUserName("admin");
        System.err.println(generatorToken(userInfo));
    }

    public static void main(String[] args) throws JOSEException, ParseException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyS2V5IjoiMTIzNDU2IiwidXNlck5hbWUiOiJhZG1pbiJ9.GNgEwqhkbeBQfFOYwRfqWT1ol1yqOg1hSLNgs_UY_cM";
        System.err.println(getFromToken(token, JwtUserInfo.class));
    }
}