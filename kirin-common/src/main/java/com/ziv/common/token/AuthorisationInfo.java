package com.ziv.common.token;

import lombok.Data;

import java.util.Set;

/**
 * 权限信息类
 *
 * @author ziv
 * @date 2019-10-14
 */
@Data
public class AuthorisationInfo {

    private JwtUserInfo userInfo;

    private Set<String> permissionsSet;

    private String token;
}
