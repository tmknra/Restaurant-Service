package com.example.hw_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.hw_5.repository")

public class Hw5Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw5Application.class, args);
    }

}
