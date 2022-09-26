package com.example.restaurant_service;

import com.example.restaurant_service.dto.DeleteUserDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.RestaurantService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitTest {
    @Autowired
    private RestaurantService restaurantService;
    // @RabbitListener(queues = "myQueue")
    // private String rabbitTest(@Payload String s){
    //     return "";
    // }
    // @RabbitListener(queues = "myQueue")
    // void rabbitTest(Message message) {
    //     // return "";
    //     // Object o = new Jackson2JsonMessageConverter().fromMessage(deleteUserDto);
    //     // System.out.println(o);
    //     System.out.println("!!!GOT IT!!! " + message);
    //     // System.out.println("user: " + user);
    // }

    // @RabbitListener(queues = "myQueue")
    // void rabbitTest(DeleteUserDto deleteUserDto) {
    //     System.out.println("!!!GOT IT!!! " + deleteUserDto);
    // }
    @RabbitListener(queues = "deleteUserQueue")
    void deleteUserListener(DeleteUserDto deleteUserDto) throws RestaurantNotFoundException {
        List<Restaurant> restaurantsByOwnerId = restaurantService.getRestaurantsByOwnerId(deleteUserDto.getOldUserId());
        Restaurant restaurant = restaurantsByOwnerId.get(0);
        restaurantService.updateRestaurant(Restaurant.builder().id(restaurantsByOwnerId.get(0).getId()).ownerId(deleteUserDto.getNewUserId()).build());
    }
}
