package com.explore.reactive.controller;

//@RestController
public class StreamController {

    /*@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> "data: {\"time\": \"" + LocalTime.now().toString() + "\", \"seq\": " + seq + "}\n\n")
                .doOnSubscribe(s -> System.out.println("Client subscribed to /stream"));
    }*/
}

