package com.yicj.study.config.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("myName", "yicj");
        MyPropertySource propertySource = new MyPropertySource("myPropertySource", propertyMap);
        environment.getPropertySources().addLast(propertySource);
    }
}
