package com.yicj.study.nacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class BootNacosApplication {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;


    public static void main(String[] args) {
        SpringApplication.run(BootNacosApplication.class, args) ;
    }
}
