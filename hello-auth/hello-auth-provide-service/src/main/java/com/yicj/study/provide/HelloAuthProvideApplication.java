package com.yicj.study.provide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class HelloAuthProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloAuthProvideApplication.class, args) ;
    }

    @GetMapping("/provide/test")
    public String test(HttpServletRequest request){
        System.out.println("------------------- success access provide service --------------------");
        return "success access provide service !" ;
    }
}
