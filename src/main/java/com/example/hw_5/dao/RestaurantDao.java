package com.example.hw_5.dao;

import com.example.hw_5.entity.Restaurant;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllRestaurants();
    String getDescriptionByName(String restName);

    void addNewRestaurant(Restaurant restaurant);
    void changeDescriptionByName(String restaurantName, String newDescription);
}
