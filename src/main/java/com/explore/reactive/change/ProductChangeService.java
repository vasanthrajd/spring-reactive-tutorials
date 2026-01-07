package com.explore.reactive.change;

import com.explore.reactive.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Configuration
public class ProductChangeService {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void matchChanges() {
        reactiveMongoTemplate
                .changeStream(Product.class)
                .watchCollection("product")
                .listen()
                .doOnNext(productChangeStreamEvent -> {
                    System.out.println("Change Detected :" + productChangeStreamEvent.getBody());
                })
                .subscribe();
    }
}
