package com.ziv.plugin.auth.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑实现接口
 * @author ziv
 * @date 2019-03-06
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails user = new MyUserDetails("admin", new BCryptPasswordEncoder().encode("admin"));
        return user;
    }
}
