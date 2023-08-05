package com.ljl.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String nameText = exchange.getRequest().getQueryParams().getFirst("nameText");
        if (nameText == null) {
            log.info("非法用户！");
        } else {
            return chain.filter(exchange);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
