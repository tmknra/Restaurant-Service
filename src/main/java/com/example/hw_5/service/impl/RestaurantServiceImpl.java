package com.example.hw_5.service.impl;

import com.example.hw_5.dao.RestaurantDao;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantDao.getAllRestaurants();
    }

    @Override
    public String getDescriptionByName(String restName) {
        return restaurantDao.getDescriptionByName(restName);
    }

    @Override
    public void addNewRestaurant(Restaurant restaurant) {
        restaurantDao.addNewRestaurant(restaurant);
    }

    @Override
    public void changeDescriptionByName(String restaurantName, String newDescription) {
        restaurantDao.changeDescriptionByName(restaurantName, newDescription);
    }

    @Override
    public void setEmailById(Long id, String email) {
        restaurantDao.setEmailById(id, email);
    }

    @Override
    public void setPhoneNumberById(Long id, String number) throws NumberParseException {
        restaurantDao.setPhoneNumberById(id, number);
    }

    @Override
    public void deleteRestaurantByName(String name) {
        restaurantDao.deleteRestaurantByName(name);
    }
}
