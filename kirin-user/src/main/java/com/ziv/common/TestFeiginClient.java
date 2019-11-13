package com.ziv.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "kirin-test")
public interface TestFeiginClient {

    @GetMapping(value = "test/useLocalCache")
    String getUeLocalCache();
}
