package com.yicj.study.gateway.model;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class GatewayFilterDefinition implements Serializable {
    // Filter name
    private String name ;

    // 对应的路由规则
    private Map<String, String> args = new LinkedHashMap<>() ;

}
