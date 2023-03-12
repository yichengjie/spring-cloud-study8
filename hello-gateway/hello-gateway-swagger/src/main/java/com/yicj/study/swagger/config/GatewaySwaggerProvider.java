package com.yicj.study.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Primary
@Component
public class GatewaySwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs" ;

    private final RouteLocator routeLocator ;

    private final GatewayProperties gatewayProperties ;

    public GatewaySwaggerProvider(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        List<String> routes = new ArrayList<>() ;
        // 取出Spring Cloud Gateway 中的route
        routeLocator.getRoutes().subscribe(route -> {
            //log.info("=======> route : {}", route);
            routes.add(route.getId()) ;
        }) ;
        //结合application.yml中路由配置，只获取有效的route节点
        return gatewayProperties.getRoutes()
                .stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .flatMap(this::parseSwaggerResource)
                .collect(Collectors.toList());

    }


    private Stream<SwaggerResource> parseSwaggerResource(RouteDefinition routeDefinition){
        return routeDefinition.getPredicates()
                .stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .map(predicateDefinition -> {
                    String id = routeDefinition.getId();
                    String location = predicateDefinition.getArgs()
                            .get(NameUtils.GENERATED_NAME_PREFIX +"0")
                            .replace("/**", API_URI) ;
                    return swaggerResource(id, location) ;
                }) ;
    }


    private SwaggerResource swaggerResource(String name, String location){
        // log.info("========> name : {}, location : {}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource() ;
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource ;
    }

}
