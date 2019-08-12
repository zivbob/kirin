package com.ziv.auth;

import lombok.Data;

import java.util.Set;

/**
 * 权限信息
 *
 * @author ziv
 * @date 2019-08-12
 */
@Data
public class AuthInfo {
    /**
     * token
     */
    private String token;

    /**
     * 权限集合
     */
    private Set<String> permsSet;
}
