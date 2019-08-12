package com.ziv.auth;

import lombok.Data;

/**
 * token信息类
 *
 * @author ziv
 * @Data 2109-08-12
 */
@Data
public class TokenInfo {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户主键
     */
    private String userKey;

}
