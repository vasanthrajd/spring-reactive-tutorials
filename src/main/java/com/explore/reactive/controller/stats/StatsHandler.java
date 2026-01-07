package com.explore.reactive.controller.stats;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StatsHandler {

    public Flux<String> getStatItems() {
        return Flux.just("Started", "Running", "Resuming", "Halt");
    }

    public Flux<String> getStatsStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> "Current Time : " + LocalTime.now().toString());
    }

    public Mono<ServerResponse> statsStream(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(getStatsStream(), String.class);
    }

    public Mono<ServerResponse> getStats(ServerRequest serverRequest) {
        return ok().body(getStatItems(), String.class);
    }


}
