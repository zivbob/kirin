package com.ziv.common.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * jwtToken工具类
 *
 * @author ziv
 * @date 2019-10-12
 */
public class JwtUtils {
    /**
     * jwt签发人
     */
    private static final String ISSUER = "manager";

    /**
     * jwt接受人
     */
    private static final String AUDIENCE = "customer";

    /**
     * 令牌过期时间（分钟）
     */
    private static final int EXPIRE = 30;

    /**
     * 令牌未启用时间（分钟）
     */
    private static final int NOT_ENABLE = 3;

    /**
     * 有效期缓冲时间（秒）
     */
    private static final int EXPIRE_BUFFER = 30;

    /**
     * 生成jwt
     * @param jwtUserInfo 用户信息
     * @param privateKey 私钥
     * @return String
     * @throws JoseException
     */
    public static String generateJwt(String privateKey, JwtUserInfo jwtUserInfo) throws JoseException {
        // 创建claims，这将是JWT的内容
        JwtClaims claims = new JwtClaims();
        // 签发人
        claims.setIssuer(ISSUER);
        // 接收人
        claims.setAudience(AUDIENCE);
        // 令牌到期时间(xx分钟后)
        claims.setExpirationTimeMinutesInTheFuture(EXPIRE);
        // 生成令牌的唯一标识符（默认16位）
        claims.setGeneratedJwtId();
        // 令牌发出/创建的时间(现在)
        claims.setIssuedAtToNow();
        // 令牌尚未有效的时间(X分钟前)
        claims.setNotBeforeMinutesInThePast(NOT_ENABLE);
        // 令牌主题
        claims.setSubject("currentUser");
        // 主题内容
        claims.setClaim("info", JSON.toJSONString(jwtUserInfo));
        claims.getClaimsMap();
        // JWT是JWS和/或JWE，使用JSON声明作为有效负载。
        JsonWebSignature jws = new JsonWebSignature();
        // JWS的有效负载是JWT声明的JSON内容
        jws.setPayload(claims.toJson());
        // 使用私钥签名的
        jws.setKey(getPrivateKey(privateKey));
        // 设置报头，促进键翻转过程的顺利进行
        // jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        // 设置保护claims完整性的签名算法RS256
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        // 签署JWS并生成紧凑的序列化或完整的JWT/JWS,如果想加密它，可以简单地将这个jwt设置为JsonWebEncryption对象的有效负载，并将cty(内容类型)头设置为“jwt”
        String jwt = jws.getCompactSerialization();
        return jwt;
    }

    /**
     * 解析jwt
     * @param jwt
     * @param publicKey 公钥
     * @return Object
     * @throws InvalidateTokenException
     */
    public static JwtUserInfo parseJwt(String jwt, String publicKey) throws InvalidateTokenException {
        // 设置解析器
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                // jwt必须包含过期时间
                .setRequireExpirationTime()
                // 在确认基于时间的claims时要留有余地，以考虑时钟倾斜（秒）
                .setAllowedClockSkewInSeconds(EXPIRE_BUFFER)
                // jwt必须包含主题
                .setRequireSubject()
                // jwt签发人验证
                .setExpectedIssuer(ISSUER)
                // jwt接收人验证
                .setExpectedAudience(AUDIENCE)
                // 公钥验证签名
                .setVerificationKey(getPublicKey(publicKey))
                // 签名算法RS256
                .setJweAlgorithmConstraints(
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                                AlgorithmIdentifiers.RSA_USING_SHA256)
                )
                // 添加自定义验证器
                .registerValidator(new MyValidator())
                // 生成解析器
                .build();
        try {
            JwtClaims claims = jwtConsumer.processToClaims(jwt);
            return JSON.parseObject(claims.getClaimValue("info").toString(), JwtUserInfo.class);
        } catch (InvalidJwtException e) {
            e.printStackTrace();
            if (e.hasExpired()) {
                throw new InvalidateTokenException("token过期");
            }
            throw new InvalidateTokenException("无效token");
        }
    }

    /**
     * 解码PublicKey
     * @param key
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解码PrivateKey
     * @param key
     * @return
     */
    public static PrivateKey  getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws JoseException, InvalidateTokenException, InterruptedException {
        /*JwtUserInfo userInfo = new JwtUserInfo();
        Set<String> perSet = new HashSet<>();
        perSet.add("admin");
        perSet.add("sysMng");
        perSet.add("test");
        userInfo.setUserName("admin");
        userInfo.setPermissions(perSet);
        String jwt = generateJwt(userInfo);
        System.out.println(jwt);
        Object obj = parseJwt(jwt);
        System.out.println(obj);*/
    }
}