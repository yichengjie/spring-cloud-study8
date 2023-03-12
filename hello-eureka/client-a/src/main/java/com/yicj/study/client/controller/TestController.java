package com.yicj.study.client.controller;

import com.yicj.study.client.model.UserInfoVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Api("测试集成Swagger")
@RestController
public class TestController {

    @ApiOperation(value = "计算+", notes = "加法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a",  value = "数字a", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "b",  value = "数字b", required = true, dataType = "Long")
    })
    @GetMapping("/add")
    public Integer add(Integer a, Integer b){
        return  a + b ;
    }

    @ApiOperation("test")
    @GetMapping("/test")
    public String prefixPath(){
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        return "https to http" ;
    }

    @GetMapping("/userInfo")
    public UserInfoVO userInfo(String username, String address){
        return new UserInfoVO(username, address) ;
    }
}
