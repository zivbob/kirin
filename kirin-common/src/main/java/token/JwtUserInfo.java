package token;

import lombok.Data;

/**
 * 用户信息
 *
 * @author ziv
 * @date 2109-10-12
 */
@Data
public class JwtUserInfo {

    private String userKey;

    private String userName;
}
