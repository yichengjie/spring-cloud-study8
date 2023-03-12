package com.yicj.study.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate ;

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        System.out.println("-----------------success access test method !----------------");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " : " + request.getHeader(headerName));
        }
        return "success access test method ";
    }

    @GetMapping("/accessProvider")
    public String accessProvider(HttpServletRequest request){
        return restTemplate.getForObject("http://provider-service//provide/test", String.class);
    }
}
