package br.com.Gateway.config;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("employee_service", r -> r
                        .path("/api/employee/**")
                        .uri("http://localhost:8083"))//Microservice running on port 8083
                .route("employee_service_swagger", r -> r
                        .path("/aggregate/employee-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/employee-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8083")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8083")) //Route to Swagger UI

                .route("client_service", r -> r
                        .path("/api/client/**")
                        .uri("http://localhost:8084"))//Microservice running on port 8084
                .route("client_service_swagger", r -> r
                        .path("/aggregate/client-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/client-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8084")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8084")) //Route to Swagger UI

                .route("property_service", r -> r
                        .path("/api/property/**")
                        .uri("http://localhost:8085"))//Microservice running on port 8085
                .route("property_service_swagger", r -> r
                        .path("/aggregate/property-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/property-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8085")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8085")) //Route to Swagger UI


                .route("search_service", r -> r
                        .path("/api/search/**")
                        .uri("http://localhost:8086"))//Microservice running on port 8086
                .route("search_service_swagger", r -> r
                        .path("/aggregate/search-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/search-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8086")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8086")) //Route to Swagger UI


                .route("email_service", r -> r
                        .path("/api/email/**")
                        .uri("http://localhost:8087"))//Microservice running on port 8087
                .route("email_service_swagger", r -> r
                        .path("/aggregate/email-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/email-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8087")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8087")) //Route to Swagger UI

                .route("whatsApp_service", r -> r
                        .path("/api/whatsApp/**")
                        .uri("http://localhost:8088"))//Microservice running on port 8088
                .route("whatsApp_service_swagger", r -> r
                        .path("/aggregate/whatsApp-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/whatsApp-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8088")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8088")) //Route to Swagger UI

                .route("auth_service", r -> r
                        .path("/api/auth/**")
                        .uri("http://localhost:8089"))//Microservice running on port 8089
                .route("auth_service_swagger", r -> r
                        .path("/aggregate/auth-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/auth-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8089")) //Route to Swagger JSON
                .route("swagger_ui", r -> r
                        .path("/swagger-ui/**")
                        .filters(f -> f.rewritePath("/swagger-ui/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8089")) //Route to Swagger UI
                .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public GlobalFilter urlBlacklistF() {
        return new UrlBlacklistFilter();
    }

}

