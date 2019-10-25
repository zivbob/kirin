package com.ziv.common.token;

import lombok.Data;

import java.util.Set;

/**
 * 用户信息
 *
 * @author ziv
 * @date 2109-10-12
 */
@Data
public class JwtUserInfo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户权限
     */
    private Set<String> permissions;
}
