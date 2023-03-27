package com.yicj.study.mvc.config;

import com.yicj.study.mvc.context.HelloLifecycle;
import com.yicj.study.mvc.properties.UserInfoProperties;
import com.yicj.study.mvc.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@EnableConfigurationProperties(UserInfoProperties.class)
public class AppConfig {

    @Bean
    public HelloLifecycle helloLifecycle(){
        HelloLifecycle helloLifecycle = new HelloLifecycle();
        log.info("-----> hello lifecycle bean init");
        return helloLifecycle ;
    }

    @Bean
    public HelloService helloService(){
        log.info("helloService init");
        return new HelloService() ;
    }
}
