package com.explore.reactive.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> helloRoutes(HelloRouteHandler helloRouteHandler) {
        return route(GET("/hello-router"), helloRouteHandler::sayHello);
    }
}
