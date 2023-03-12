package com.yicj.study.feign.remote.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "client-b", contextId = "helloFeignClient")
public interface HelloFeignClient {

    @GetMapping("/test")
    String prefixPath() ;
}
