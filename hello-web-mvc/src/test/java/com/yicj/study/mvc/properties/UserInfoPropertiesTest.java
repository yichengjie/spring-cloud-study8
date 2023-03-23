package com.yicj.study.mvc.properties;

import com.yicj.study.mvc.BaseJunitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserInfoPropertiesTest extends BaseJunitTest {

    @Autowired
    private UserInfoProperties userInfoProperties ;

    @Test
    public void hello(){
        log.info("userInfoProperties : {}", userInfoProperties);
    }
}
