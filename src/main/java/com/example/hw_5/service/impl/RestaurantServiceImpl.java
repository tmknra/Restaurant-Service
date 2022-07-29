package com.example.hw_5.service.impl;

import com.example.hw_5.dao.RestaurantDao;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public List<Restaurant> getAll() {
        return restaurantDao.getAll();
    }

    @Override
    public String getDescription(String restName) {
        return restaurantDao.getDescription(restName);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurantDao.addRestaurant(restaurant);
    }

    @Override
    public void changeRestaurantDescription(String restaurantName, String newDescription) {
        restaurantDao.changeRestaurantDescription(restaurantName, newDescription);
    }
}
