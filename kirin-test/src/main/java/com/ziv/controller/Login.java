package com.ziv.controller;

import com.ziv.plugin.auth.security.auth.JwtTokenUtil;
import com.ziv.plugin.auth.security.auth.MyUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.JsonResult;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "sys")
public class Login {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "login")
    public JsonResult<String> login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        MyUserDetails user = new MyUserDetails(username, password);
        String token = jwtTokenUtil.generateToken(user);
        return JsonResult.success(token);
    }

    @GetMapping(value = "hello")
    public JsonResult<String> hello() {
        return JsonResult.success("hello guys");
    }
}
