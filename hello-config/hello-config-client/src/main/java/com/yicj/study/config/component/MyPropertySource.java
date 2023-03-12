package com.yicj.study.config.component;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.Map;

public class MyPropertySource extends EnumerablePropertySource<Map<String, String>> {

    public MyPropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    protected MyPropertySource(String name) {
        super(name);
    }

    // 返回所有的属性名称
    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[source.size()]);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
