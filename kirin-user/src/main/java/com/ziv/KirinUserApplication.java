package com.ziv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KirinUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(KirinUserApplication.class, args);
    }
}
