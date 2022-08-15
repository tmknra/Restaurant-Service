package com.example.hw_5.dao;

import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.entity.FoundationDateIsExpiredException;
import com.example.hw_5.exception.entity.PhoneNumberNotRuException;
import com.google.i18n.phonenumbers.NumberParseException;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> getAllRestaurants();
    String getDescriptionByName(String restName);
    String getFoundationDateById(Long id);

    void addNewRestaurant(Restaurant restaurant) throws FoundationDateIsExpiredException;

    void changeDescriptionByName(String restaurantName, String newDescription);
    void setEmailById(Long id, String email);
    void setPhoneNumberById(Long id, String number) throws NumberParseException, PhoneNumberNotRuException;
    void setFoundationDateById(Long id, String date) throws FoundationDateIsExpiredException;

    void deleteRestaurantByName(String name);
}
