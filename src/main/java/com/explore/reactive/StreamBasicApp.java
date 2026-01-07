package com.explore.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamBasicApp {
    public static void main(String[] args) {
        System.out.println("==Reactive Stream with Project Reactor==");
        //
        Mono<String> mono = Mono.just("Result were outcome of Mono");
        mono.map(String::toLowerCase).subscribe(System.out::println);

        Flux<String> flux = Flux.just("Result", "Using", "Flux", "To Show", "Outcome");
        flux.filter(s -> s.length() >=3)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        Flux<String> delayedFlux = Flux.just("Result", "Using", "Flux with Mono", "And FlatMap", "Result","Outcome")
                .flatMap(name -> Mono.just("Parallel Thread Result  -> " + name + " >> " + atomicInteger.addAndGet(1)).delayElement(Duration.ofMillis(1500))).log();

        delayedFlux.subscribe(System.out::println);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep Interrupted: " + e.getMessage());
        }
        /*System.out.println(delayedFlux.index());
        System.out.println(delayedFlux.cache(5));
        System.out.println(delayedFlux.take(10));
        System.out.println(delayedFlux.checkpoint());*/
        System.out.println("=== Blocking Thread Execution ===");
        Flux<String> blockingFlux = Flux.just("Flux", "With", "Map", "And Blocking", "Thread");
        atomicInteger.getAndSet(0);
        blockingFlux.map(name -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("Error Log "+ e.getMessage());
            }
            return "Processed with Delay for " + name + " in Parallel Thread >> " + atomicInteger.addAndGet(1);
        }).log().subscribe(System.out::println);


    }
}
