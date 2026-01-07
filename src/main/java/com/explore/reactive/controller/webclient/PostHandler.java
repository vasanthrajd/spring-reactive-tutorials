package com.explore.reactive.controller.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PostHandler {

    @Autowired
    private WebClient webClient;

    public Mono<ServerResponse> fetchPosts(ServerRequest serverRequest) {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/broken-endpoint")
                .retrieve()
                .bodyToMono(String.class)
                .log("Body to Mono")
                .timeout(Duration.ofSeconds(1))
                .log("Timeout Duration ")
                .retry(2)
                .log("Retry Count")
                .onErrorResume(ex -> Mono.just("Fallback Response: Server is not available"))
                .log("On Error Resume")
                .flatMap(response -> ok().bodyValue(response))
                .log("On Completion");
    }
}
