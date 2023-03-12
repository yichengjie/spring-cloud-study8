package com.yicj.study.feign.remote.feign;

import com.yicj.study.feign.config.FeignConfig;
import com.yicj.study.feign.model.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "testFeignClient"
        , name = "client-a"
        , configuration = FeignConfig.class
)
public interface TestFeignClient {

    @GetMapping("/add")
    Integer add(
            @RequestParam("a") Integer a,
            @RequestParam("b") Integer b) ;

    @GetMapping("/add")
    ResponseEntity<byte []> addCompress(
            @RequestParam("a") Integer a,
            @RequestParam("b") Integer b) ;

    @GetMapping("/userInfo")
    UserInfoVO userInfo(
            @RequestParam("username") String username,
            @RequestParam("address") String address) ;

    @GetMapping(value = "/userInfo")
    //@RequestLine("GET /userInfo")
    //@Headers({"Content-Type: text/xml;charset=UTF-8"})
    ResponseEntity<byte []> userInfoCompress(
            @RequestParam("username") String username,
            @RequestParam("address") String address) ;

}
