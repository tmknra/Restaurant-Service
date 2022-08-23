package com.example.restaurant_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.restaurant_service.repository")

public class RestaurantServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApp.class, args);
    }

}
