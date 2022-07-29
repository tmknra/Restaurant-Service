package com.example.hw_5.dao;

import com.example.hw_5.entity.Restaurant;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAll();
    String getDescription(String restName);


    void addRestaurant(Restaurant restaurant);
    void changeRestaurantDescription(String restaurantName, String newDescription);
}
