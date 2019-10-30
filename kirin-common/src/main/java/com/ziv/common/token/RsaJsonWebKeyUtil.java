package com.ziv.common.token;

import com.alibaba.fastjson.JSON;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

import java.util.HashMap;
import java.util.Map;

/**
 * 秘钥生成类（单系统是可利用本类获取jwk秘钥）
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
            this.rsaJsonWebKey.setKeyId("kid");
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }

    public static RsaJsonWebKey getRsaJsonWebKey() {
        return SingletonHolder.key;
    }
}
