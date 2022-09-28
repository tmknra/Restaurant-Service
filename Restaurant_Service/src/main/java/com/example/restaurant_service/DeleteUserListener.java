package com.example.restaurant_service;

import com.example.restaurant_service.dto.DeleteUserDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.RestaurantService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteUserListener {
    @Autowired
    private RestaurantService restaurantService;

    @RabbitListener(queues = "deleteUserQueue")
    void deleteUserListener(DeleteUserDto deleteUserDto) throws RestaurantNotFoundException {
        List<Restaurant> restaurantsByOwnerId = restaurantService.getRestaurantsByOwnerId(deleteUserDto.getOldUserId());
        Restaurant restaurant = restaurantsByOwnerId.get(0);
        restaurantService.updateRestaurant(
                RestaurantInDto.builder()
                        .ownerId(deleteUserDto.getNewUserId())
                        .build(),
                restaurant.getId());
    }
}