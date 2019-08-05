package com.ziv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.JsonResult;

@RestController
@RequestMapping(value = "test")
public class TestController {

    @GetMapping
    public JsonResult get() {
        return JsonResult.success("you got it");
    }
}
