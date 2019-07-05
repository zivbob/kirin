package com.ziv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class KirinConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KirinConfigServerApplication.class, args);
    }

}
