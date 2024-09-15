package br.com.Gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UrlBlacklistFilter implements GlobalFilter {

    private final List<String> blockedUrls = List.of("/sales", "/employee", "/client",
            "/search", "/whatsApp", "/email", "/property","/","");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().value();
        System.out.println(requestPath);
        if (!requestPath.contains("/v3/api-docs")) {
            for (String path:blockedUrls) {
                if (requestPath.startsWith(path)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }
        }

        return chain.filter(exchange);
    }
}