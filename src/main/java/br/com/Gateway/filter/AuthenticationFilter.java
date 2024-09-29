package br.com.Gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator valid;

    @Autowired
    @Lazy
    private AuthService authService;

    private final LocalDateTime dataTime = LocalDateTime.now();



    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return (exchange, chain) -> {
            if (valid.isSecured.test(exchange.getRequest())) {

                var originHeader = exchange.getRequest().getURI();

                log.info(originHeader+" "+ dataTime);


                // Checks if the Authorization header is present
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("Missing authorization header: "+ dataTime);
                    return Mono.error(new RuntimeException("Missing authorization header"));
                }

                // Gets the token from the authorization header
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7).replace("Bearer ", "");
                } else {
                    log.error("Invalid authorization header format: "+ dataTime);
                    return Mono.error(new RuntimeException("Invalid authorization header format"));
                }

                //
                //Perform authentication reactively
                return authService.authenticate(authHeader)
                        .then(chain.filter(exchange))  // Continue reactive flow if authentication is successful
                        .onErrorResume(e -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            log.error("UNAUTHORIZED USER: "+ dataTime);
                            return exchange.getResponse().setComplete();
                        });
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
