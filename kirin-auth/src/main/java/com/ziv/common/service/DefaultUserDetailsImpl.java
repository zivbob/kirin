package com.ziv.common.service;

import com.ziv.entity.DefaultUserDetails;
import com.ziv.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultUserDetailsImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("登陆用户：" + username);
        if ("zivAdmin".equals(username)) {
            SysUser user = new SysUser();
            user.setUsername("zivAdmin");
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            return new DefaultUserDetails(user);
        }
        return null;
    }
}
