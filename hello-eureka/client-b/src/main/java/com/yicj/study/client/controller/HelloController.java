package com.yicj.study.client.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("服务BHello接口")
public class HelloController {

    @ApiOperation("test")
    @GetMapping("/test")
    public String prefixPath(){

        return "hello client b service" ;
    }
}
