package com.yicj.study.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 在LoadBalancerClientFilter执行前将https转为http
 * {@link LoadBalancerClientFilter}
 */
//@Component
public class Https2HttpFilter implements GlobalFilter, Ordered {

    private static final int HTTPS_TO_HTTP_FILTER_ORDER = 10099 ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI originalUri = exchange.getRequest().getURI();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutateReqBuilder = request.mutate();
        String forwardUri = request.getURI().toString();
        if (forwardUri != null && forwardUri.startsWith("https")){
            try {
                URI mutateUri = new URI("http",
                   originalUri.getUserInfo(),
                   originalUri.getHost(),
                   originalUri.getPort(),
                   originalUri.getPath(),
                   originalUri.getQuery(),
                   originalUri.getFragment()
                ) ;
                mutateReqBuilder.uri(mutateUri) ;
            } catch (URISyntaxException e) {
                throw new IllegalStateException(e.getMessage(), e) ;
            }
        }
        ServerHttpRequest mutateRequest = mutateReqBuilder.build();
        return chain.filter(exchange.mutate().request(mutateRequest).build());
    }

    /**
     * 由于{@link LoadBalancerClientFilter}的order是 10100，要在LoadBalancerClientFilter执行前将Https修改为http,
     * 需要设置order为10099
     * @return
     */
    @Override
    public int getOrder() {
        return HTTPS_TO_HTTP_FILTER_ORDER;
    }
}
