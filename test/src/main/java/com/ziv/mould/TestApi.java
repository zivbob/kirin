package com.ziv.mould;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping(value = "test")
    public Object test() {
        return "hello";
    }
}
