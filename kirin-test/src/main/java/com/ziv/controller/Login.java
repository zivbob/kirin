package com.ziv.controller;

import com.ziv.common.response.JsonResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "sys")
public class Login {

    @GetMapping(value = "hello")
    @PreAuthorize(value = "hasAuthority('test')")
    public JsonResult<String> hello() {
        return JsonResult.success("hello guys");
    }
}
