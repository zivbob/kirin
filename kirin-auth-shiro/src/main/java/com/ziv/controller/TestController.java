package com.ziv.controller;

import com.ziv.common.response.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {

    @GetMapping
    public JsonResult get() {
        return JsonResult.success("you got it");
    }

    @RequiresPermissions(value = {"test"})
    @GetMapping("per")
    public JsonResult getInfo() {
        Subject subject = SecurityUtils.getSubject();
        return JsonResult.success(subject.getPrincipal());
    }
}
