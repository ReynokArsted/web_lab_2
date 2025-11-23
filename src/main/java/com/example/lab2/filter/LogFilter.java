package com.example.lab2.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LogFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("from WebFilter - Client request: " + exchange.getRequest().getURI());
        return chain.filter(exchange)
            .doOnTerminate(() -> log.info("from WebFilter - Server response: " + exchange.getResponse().getStatusCode()));
    }
}
