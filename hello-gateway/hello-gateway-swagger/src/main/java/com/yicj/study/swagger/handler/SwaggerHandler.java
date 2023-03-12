package com.yicj.study.swagger.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration ;
    @Autowired(required = false)
    private UiConfiguration uiConfiguration ;
    @Autowired
    private SwaggerResourcesProvider gatewaySwaggerProvider ;

    @GetMapping("/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration(){
        SecurityConfiguration securityConfiguration = Optional.ofNullable(this.securityConfiguration)
                .orElse(SecurityConfigurationBuilder.builder().build());
        return Mono.just(new ResponseEntity<>(securityConfiguration, HttpStatus.OK)) ;
    }

    @GetMapping("/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration(){
        UiConfiguration uiConfiguration = Optional.ofNullable(this.uiConfiguration)
                .orElse(UiConfigurationBuilder.builder().build());
        return Mono.just(new ResponseEntity<>(uiConfiguration, HttpStatus.OK)) ;
    }

    @GetMapping("")
    public Mono<ResponseEntity<SwaggerResource>> swaggerResource(){
        List<SwaggerResource> resources = gatewaySwaggerProvider.get();
        return Mono.just(new ResponseEntity(resources, HttpStatus.OK)) ;
    }

}
