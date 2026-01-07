package com.explore.reactive.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class ProductDataInitializer {

    @Bean
    CommandLineRunner init(ProductRepository productRepository) {
        return args -> {
            productRepository
                    .deleteAll()

                    .thenMany(
                            Flux.just(
                                new Product(null, "Laptop", 1200),
                                new Product(null, "Mobile", 700),
                                new Product(null, "EarBuds", 200)
                            ).flatMap(productRepository::save)
                    )
                    .thenMany(productRepository.findAll())
                    .log()
                    .subscribe(System.out::println);
        };
    }
}
