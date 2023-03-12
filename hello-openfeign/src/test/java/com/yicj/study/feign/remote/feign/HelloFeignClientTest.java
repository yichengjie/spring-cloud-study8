package com.yicj.study.feign.remote.feign;

import com.yicj.study.feign.BaseJunitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class HelloFeignClientTest extends BaseJunitTest {

    @Autowired
    private HelloFeignClient helloFeignClient ;

    @Test
    public void prefixPath(){
        String value = helloFeignClient.prefixPath();
        log.info("value : {}", value);
    }
}
