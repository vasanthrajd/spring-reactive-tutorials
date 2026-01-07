package com.explore.reactive.controller.stats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StatsRouter {

    @Bean
    public RouterFunction<ServerResponse> statsRoutes(StatsHandler statsHandler) {
        return route()
                .GET(("/stats"),statsHandler::getStats)
                .GET(("/stats-stream"),statsHandler::statsStream)
                .build();
    }
}
