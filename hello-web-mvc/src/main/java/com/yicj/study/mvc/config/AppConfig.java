package com.yicj.study.mvc.config;

import com.yicj.study.mvc.properties.UserInfoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserInfoProperties.class)
public class AppConfig {


}
