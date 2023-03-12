package com.yicj.study.feign.loadbalancer;

import com.yicj.study.feign.BaseJunitTest;
import com.yicj.study.feign.remote.feign.TestFeignClient;
import feign.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import java.io.IOException;

@Slf4j
public class LoadBalancerFeignClientTest extends BaseJunitTest {

    @Autowired
    private Client client ;

    @Test
    public void execute() throws IOException {
        Class<?> type = TestFeignClient.class;
        String name = "client-a";
        String url = "http://client-a";
        Target.HardCodedTarget target =  new Target.HardCodedTarget(type, name, url) ;
        // FieldQueryMapEncoder
        // BuildTemplateByResolvingArgs buildTemplate = new ReflectiveFeign.BuildTemplateByResolvingArgs(md, queryMapEncoder, target);
        RequestTemplate requestTemplate = new RequestTemplate() ;
        requestTemplate.method(Request.HttpMethod.GET) ;
        requestTemplate.uri("/add") ;
        Request request = target.apply(requestTemplate);
        Request.Options options = null  ;
        Response response = client.execute(request, options);
        log.info("response : {}", response);
    }
}
