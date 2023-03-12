package com.yicj.study.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher ;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter ;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher ;
    }

    // 增加路由
    public String add(RouteDefinition definition){
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success" ;
    }

    public String update(RouteDefinition definition){
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId())).subscribe();
        }catch (Exception e){
            return "update fail , not find route routeId : " + definition.getId() ;
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success" ;
        }catch (Exception e){
            return "update route fail " ;
        }
    }


    public Mono<ResponseEntity<Object>> delete(String id){
        return routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }
}
