package com.ziv.common.service;

import com.ziv.common.config.MyUserDetail;
import com.ziv.common.config.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("用户登录：" + userName);
        SysUser user = new SysUser();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode("123456"));
        MyUserDetail userDetail = new MyUserDetail(user);
        return userDetail;
    }
}
