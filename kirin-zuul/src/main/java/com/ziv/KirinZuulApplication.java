package com.ziv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class KirinZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(KirinZuulApplication.class, args);
    }

}
