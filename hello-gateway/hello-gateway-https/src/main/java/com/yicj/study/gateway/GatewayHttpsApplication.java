package com.yicj.study.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayHttpsApplication {

    public static void main(String[] args) {

        SpringApplication.run(GatewayHttpsApplication.class, args) ;
    }
}
