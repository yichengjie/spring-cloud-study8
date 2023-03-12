package com.yicj.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = ConfigSupportProperties.CONFIG_PREFIX)
public class ConfigSupportProperties {

    public static final String CONFIG_PREFIX = "spring.cloud.config.backup" ;

    private final String DEFAULT_FILE_NAME = "fallback.properties" ;

    private boolean enable = false ;

    private String fallbackLocation ;

    public String getFallbackLocation() {
        return fallbackLocation;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setFallbackLocation(String fallbackLocation) {
        // 如果只填写路径，那么就为其添加一个默认的文件名称
        if (!fallbackLocation.contains(".")){
            this.fallbackLocation = fallbackLocation + DEFAULT_FILE_NAME ;
            return;
        }
        this.fallbackLocation = fallbackLocation;
    }
}
