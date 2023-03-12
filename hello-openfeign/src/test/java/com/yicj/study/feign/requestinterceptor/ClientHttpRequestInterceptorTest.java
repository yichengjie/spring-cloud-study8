package com.yicj.study.feign.requestinterceptor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ClientHttpRequestInterceptorTest {

    @Test
    public void getForObject(){

        ClientHttpRequestInterceptor i1 = (request, body, execution) -> {
            log.info("拦截器1开始....");
            ClientHttpResponse execute = execution.execute(request, body);
            log.info("拦截器1结束....");
            return execute;
        };

        ClientHttpRequestInterceptor i2 = (request, body, execution) -> {
            log.info("拦截器2开始....");
            ClientHttpResponse execute = execution.execute(request, body);
            log.info("拦截器2结束....");
            return execute;
        };
        //
        RestTemplate restTemplate = new RestTemplate() ;
        restTemplate.getInterceptors().add(i1) ;
        restTemplate.getInterceptors().add(i2) ;
        Integer value = restTemplate.getForObject("http://localhost:7070/add?a=1&b=2", Integer.class);
        log.info("value : {}", value);
    }
}
