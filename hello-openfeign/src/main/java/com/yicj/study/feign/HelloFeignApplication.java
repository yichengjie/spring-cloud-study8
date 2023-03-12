package com.yicj.study.feign;

import com.yicj.study.feign.decoder.FeignResponseDecoder;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableFeignClients
@SpringCloudApplication
public class HelloFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloFeignApplication.class, args) ;
    }

    //@Bean
    public Decoder GZIPResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        Decoder decoder = new FeignResponseDecoder(new SpringDecoder(messageConverters));
        return decoder;
    }
}
