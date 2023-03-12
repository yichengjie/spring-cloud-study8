package com.yicj.study.feign.loadbalancer;

import com.yicj.study.feign.BaseJunitTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@Slf4j
public class LoadBalancerClientTest extends BaseJunitTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient ;


}
