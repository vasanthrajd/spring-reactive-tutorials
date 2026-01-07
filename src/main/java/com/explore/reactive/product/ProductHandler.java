package com.explore.reactive.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductHandler {

    @Autowired
    ProductRepository productRepository;

    public Flux<Product> getAllProducts() {
        return productRepository.findAll().log();
    }

    public Mono<ServerResponse> fetchAllProducts(ServerRequest serverRequest) {
        Flux<Product> products = getAllProducts();
        return ok().body(products, Product.class);
    }

    public Mono<ServerResponse> addProducts(ServerRequest serverRequest) {
        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);
        return productMono
                .flatMap(productRepository::save)
                .flatMap(saved ->
                        ServerResponse.created(URI.create("/products/" + saved.getId()))
                                .bodyValue(saved)
                );
   }

   public Mono<ServerResponse> testProduct(ServerRequest serverRequest) {
        return productRepository.save(new Product(null, "Tablet", 123.00))
                .flatMap(product ->
                    ServerResponse.created(URI.create("/products/" + product.getId()))
                            .bodyValue(product)
                );
   }

   public Mono<ServerResponse> getProduct(ServerRequest serverRequest) {
       String id = serverRequest.pathVariable("id");
       return productRepository.findById(id)
               .flatMap(product -> ServerResponse.ok().bodyValue(product))
               .switchIfEmpty(ServerResponse.notFound().build());
   }
}
