package com.yicj.study.sentinel.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yicj.study.sentinel.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public void doSomething() {
        try(Entry entry = SphU.entry("doSomething")){
            // 业务逻辑处理
            log.info("Hello world {}", System.currentTimeMillis());
        }catch (BlockException e){
            // 处理被流控的逻辑
            log.error("被流控了!!!");
        }
    }
}
