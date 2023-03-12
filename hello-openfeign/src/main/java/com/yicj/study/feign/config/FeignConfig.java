package com.yicj.study.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FeignConfig {

    @Bean
    public Logger.Level feignLevel(){

        return Logger.Level.FULL ;
    }
}
