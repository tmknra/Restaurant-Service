package com.example.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.user_service.repository")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    // @Bean
    // MessageConverter messageConverter(ObjectMapper objectMapper) {
    //     // jackson2JsonMessageConverter.;
    //     return new Jackson2JsonMessageConverter(objectMapper);
    // }

    // @Bean("myQueue")
    // public Queue myQueue() {
    //     return new Queue("myQueue", false);
    // }

    @Bean("deleteUserQueue")
    public Queue myQueue() {
        return new Queue("deleteUserQueue", false);
    }



    // @RabbitListener(queues = "myQueue")
    // public void listen(String in) {
    //     System.out.println(in);
    // }

}
