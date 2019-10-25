package com.ziv.common.token;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

import java.util.UUID;

/**
 * 秘钥生成类
 *
 * @author ziv
 * @date 2019-10-24
 */
public class RsaJsonWebKeyUtil {

    /**
     * 秘钥
     */
    private RsaJsonWebKey rsaJsonWebKey;

    private static class SingletonHolder {
        private static RsaJsonWebKey key = new RsaJsonWebKeyUtil().rsaJsonWebKey;
    }

    private RsaJsonWebKeyUtil() {
        try {
            /**
             * 生成一个封装在JWK中的RSA密钥对，用于JWT的签名和验证
             */
            this.rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            // 给JWK一个密钥ID (kid)，这是最礼貌的做法
            this.rsaJsonWebKey.setKeyId(UUID.randomUUID().toString());
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }

    public static RsaJsonWebKey getRsaJsonWebKey() {
        return SingletonHolder.key;
    }
}
