package com.explore.reactive.controller.sse;

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
public class TimeHandler {

    public Flux<String> getTimeStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> "Current Time : " + LocalTime.now().toString());
    }

    public Mono<ServerResponse> streamTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(getTimeStream(), String.class);
    }
}
