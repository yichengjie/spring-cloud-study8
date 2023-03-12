package com.yicj.study.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public Logger.Level feignLevel(){

        return Logger.Level.FULL ;
    }
}
