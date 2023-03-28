package com.yicj.study.jdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.yicj.study.jdbc.repository.mapper")
@SpringBootApplication
public class JdbcApplication {


    public static void main(String[] args) {

        SpringApplication.run(JdbcApplication.class, args) ;
    }
}
