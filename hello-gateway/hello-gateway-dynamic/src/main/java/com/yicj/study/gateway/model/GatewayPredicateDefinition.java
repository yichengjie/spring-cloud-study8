package com.yicj.study.gateway.model;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class GatewayPredicateDefinition implements Serializable {
    // 断言对应的Name
    private String name ;

    // 配置的断言规则
    private Map<String, String> args = new LinkedHashMap<>() ;

}
