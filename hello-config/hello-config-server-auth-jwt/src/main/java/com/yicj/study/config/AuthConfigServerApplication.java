package com.yicj.study.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class AuthConfigServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthConfigServerApplication.class, args) ;
    }
}
