package com.yicj.study.config.component;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class MyPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        Map<String, String> properties = new HashMap<>() ;
        properties.put("myAddress", "BJS") ;
        return new MyPropertySource("myPropertySourceLocator", properties) ;
    }
}
