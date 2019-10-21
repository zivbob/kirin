package com.ziv.plugin.auth.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 用户信息实体类
 *
 * @author ziv
 * @date 2019-10-08
 */
public class MyUserDetails implements UserDetails {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Set<String> permission;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permission.stream().map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toSet());
    }

    public MyUserDetails (String username, String password, Set<String> permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}