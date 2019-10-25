package com.ziv.common.token;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodeValidator;
import org.jose4j.jwt.consumer.JwtContext;

/**
 * 自定义jwt验证器
 *
 * @author ziv
 * @date 2019-10-24
 */
public class MyValidator implements ErrorCodeValidator {
    @Override
    public Error validate(JwtContext jwtContext) throws MalformedClaimException {
        // TODO 补充验证逻辑
        System.err.println("claims" + jwtContext.getJwtClaims());
        // 刷新有效期
        jwtContext.getJwtClaims().setExpirationTimeMinutesInTheFuture(30);
        return null;
    }
}
