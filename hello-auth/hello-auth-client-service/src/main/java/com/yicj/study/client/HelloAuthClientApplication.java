package com.yicj.study.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloAuthClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloAuthClientApplication.class, args) ;
    }
}
