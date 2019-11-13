package com.ziv.controller;

import com.ziv.common.TestFeiginClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "test")
public class TestClass {

    @Resource
    private TestFeiginClient testFeiginClient;

    @GetMapping(value = "useLocalCache")
    public String getUseLocalCache() {
        return testFeiginClient.getUeLocalCache();
    }
}
