package com.example.restaurant_service;

import com.example.restaurant_service.dto.DeleteRestaurantOwnerDto;
import com.example.restaurant_service.dto.UpdateRestaurantOwnerDto;
import com.example.restaurant_service.service.RestaurantService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class UpdateOwnerListener {

    private final RestaurantService restaurantService;

    public UpdateOwnerListener(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RabbitListener(queues = "updateOwnerQueue")
    void updateOwnerListener(@Payload UpdateRestaurantOwnerDto updateRestaurantOwnerDto) {
        restaurantService.updateOwnerForRestaurants(updateRestaurantOwnerDto);
    }

    @RabbitListener(queues = "deleteOwnerQueue")
    void deleteOwnerListener(@Payload DeleteRestaurantOwnerDto deleteRestaurantOwnerDto) {
        restaurantService.deleteOwnerFromRestaurants(deleteRestaurantOwnerDto);
    }
}
