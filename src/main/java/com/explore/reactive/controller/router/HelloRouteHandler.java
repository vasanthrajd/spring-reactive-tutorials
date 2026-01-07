package com.explore.reactive.controller.router;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class HelloRouteHandler {

    public Mono<ServerResponse> sayHello(ServerRequest serverRequest) {
        return ok().bodyValue("Hello from Route Handler");
    }
}
