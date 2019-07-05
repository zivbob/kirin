package com.ziv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

@EnableEurekaClient
@EnableZipkinServer
@SpringBootApplication
public class KirinZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(KirinZipkinApplication.class, args);
    }

}
