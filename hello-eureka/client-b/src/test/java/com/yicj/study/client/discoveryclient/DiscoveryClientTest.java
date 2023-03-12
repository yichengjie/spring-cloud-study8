package com.yicj.study.client.discoveryclient;

import com.yicj.study.client.BaseJunitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@Slf4j
public class DiscoveryClientTest extends BaseJunitTest {

    @Autowired
    private DiscoveryClient discoveryClient ;

    @Test
    public void getInstances(){
        String serviceId = "client-a" ;
        List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
        log.info("instance size : {}", list.size());
        list.forEach(instance -> log.info("instance : host : {}, port : {}", instance.getHost(), instance.getPort()));

        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);
    }
}
