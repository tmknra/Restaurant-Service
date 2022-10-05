package com.example.restaurant_service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.restaurant_service.repository")
@EnableFeignClients
public class RestaurantServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApp.class, args);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("updateOwnerQueue")
    public Queue updateOwnerQueue() {
        return new Queue("updateOwnerQueue", false);
    }

    @Bean("deleteOwnerQueue")
    public Queue deleteOwnerQueue() {
        return new Queue("deleteOwnerQueue", false);
    }
}
