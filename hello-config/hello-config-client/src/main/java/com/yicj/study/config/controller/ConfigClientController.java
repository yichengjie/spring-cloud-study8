package com.yicj.study.config.controller;

import com.yicj.study.config.properties.ConfigInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigClientController {

    @Autowired
    private ConfigInfoProperties configInfoProperties ;

    @GetMapping("/getConfigInfo")
    public String getConfigInfo(){

        return configInfoProperties.getConfig() ;
    }

}
