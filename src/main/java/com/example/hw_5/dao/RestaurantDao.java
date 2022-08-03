package com.example.hw_5.dao;

import com.example.hw_5.entity.Restaurant;
import com.google.i18n.phonenumbers.NumberParseException;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllRestaurants();
    String getDescriptionByName(String restName);

    void addNewRestaurant(Restaurant restaurant);

    void changeDescriptionByName(String restaurantName, String newDescription);
    void setEmailById(Long id, String email);
    void setPhoneNumberById(Long id, String number) throws NumberParseException;

    void deleteRestaurantByName(String name);
}
