package com.explore.reactive.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRoute {

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return route()
                .path("/product", builder -> {
                    // Specific fixed routes first
                    builder.GET("/list", productHandler::fetchAllProducts)
                            .GET("/test", productHandler::testProduct)
                            // Variable route last to avoid capturing fixed paths
                            .GET("/{id}", productHandler::getProduct)
                            // POST on base path /product
                            .POST(productHandler::addProducts);
                })
                .build();
    }
}
