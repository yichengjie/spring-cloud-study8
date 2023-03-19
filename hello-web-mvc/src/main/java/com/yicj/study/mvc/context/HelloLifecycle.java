package com.yicj.study.mvc.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class HelloLifecycle implements /*Smart*/Lifecycle {


    @Override
    public void start() {
        log.info("start ....");
    }

    @Override
    public void stop() {
        log.info("stop .... ");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
