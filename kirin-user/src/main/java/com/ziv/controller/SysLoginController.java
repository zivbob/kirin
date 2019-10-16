package com.ziv.controller;

import com.nimbusds.jose.JOSEException;
import com.ziv.common.response.JsonResult;
import com.ziv.common.response.ResultCode;
import com.ziv.common.token.AuthorisationInfo;
import com.ziv.common.token.JwtUserInfo;
import com.ziv.common.token.JwtUtils;
import com.ziv.entity.SysUser;
import com.ziv.plugin.redis.utils.RedisUtils;
import com.ziv.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统登录控制类
 *
 * @author ziv
 * @date 2109-10-14
 */
@RestController
@RequestMapping(value = "sys")
public class SysLoginController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping(value = "login")
    public JsonResult<AuthorisationInfo> login (String userName, String password) throws JOSEException {
        JsonResult result;
        SysUser user = sysUserService.selectByUserName(userName);
        if (user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            JwtUserInfo userInfo = new JwtUserInfo();
            userInfo.setUserKey(user.getUserKey());
            userInfo.setUserName(user.getUserName());
            Set<String> perSet = new HashSet<>();
            perSet.add("admin");
            perSet.add("sysMng");
            perSet.add("test");

            AuthorisationInfo authorisationInfo = new AuthorisationInfo();
            authorisationInfo.setUserInfo(userInfo);
            authorisationInfo.setPermissionsSet(perSet);
            // 生成token
            String token = JwtUtils.generatorToken(authorisationInfo);
            // token存入缓存
            redisUtils.setWithDefaultExpire(token, authorisationInfo);
            authorisationInfo.setToken(token);
            result = JsonResult.success(authorisationInfo);
        } else{
            result = JsonResult.error(ResultCode.USER_NOT_EXIST);
        }
        return result;
    }

    @PostMapping(value = "registry")
    public JsonResult registry(SysUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        sysUserService.add(user);
        return JsonResult.success();
    }
}