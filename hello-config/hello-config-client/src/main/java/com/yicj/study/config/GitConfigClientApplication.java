package com.yicj.study.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
public class GitConfigClientApplication  {


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GitConfigClientApplication.class);
        //springApplication.addInitializers(new HelloApplicationContextInitializer());
        ConfigurableApplicationContext run = springApplication.run(args);
        Ser bean = run.getBean(Ser.class);
        System.err.println("===> bean my name : " + bean);

    }

    @Data
    @Component
    public static class Ser{
        @Value("${myName}")
        private String myName;

        @Value("${myAddress}")
        private String myAddress ;
    }
}
