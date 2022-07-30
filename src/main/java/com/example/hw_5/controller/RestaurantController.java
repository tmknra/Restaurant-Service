package com.example.hw_5.controller;

import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Resource
    private RestaurantService restaurantService;

    @GetMapping("/all")
    public List<Restaurant> getAll(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{name}")
    public String getDescription(@PathVariable String name){
        return restaurantService.getDescriptionByName(name);
    }

    @PutMapping("/new")
    public void addNewRestaurant(@RequestBody Restaurant restaurant){
        restaurantService.addNewRestaurant(restaurant);
    }

    @PutMapping("/change_description")
    public void changeDescription(@RequestBody Restaurant restaurant){
        restaurantService.changeDescriptionByName(restaurant.getName(), restaurant.getDescription());
    }
}
