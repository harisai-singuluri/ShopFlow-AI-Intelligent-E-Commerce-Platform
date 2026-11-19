package com.shopflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the ShopFlow Spring Boot application.
 *
 * <p>All configuration is externalised via {@code application.yml}. This class only
 * bootstraps the Spring context and should remain minimal – a senior‑engineer practice
 * that keeps the startup class free of business logic.</p>
 */
@SpringBootApplication
public class ShopFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFlowApplication.class, args);
    }
}
