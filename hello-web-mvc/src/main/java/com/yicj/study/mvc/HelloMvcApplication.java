package com.yicj.study.mvc;

import com.yicj.study.mvc.context.HelloLifecycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class HelloMvcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(HelloMvcApplication.class, args);
        context.start();
    }

    @Bean
    public HelloLifecycle helloLifecycle(){
        HelloLifecycle helloLifecycle = new HelloLifecycle();
        log.info("-----> hello lifecycle bean init");
        return helloLifecycle ;
    }

}
