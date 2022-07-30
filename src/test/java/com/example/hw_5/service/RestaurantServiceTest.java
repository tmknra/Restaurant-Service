package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantServiceTest extends Hw5ApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void addNewRestaurant() {
        Restaurant restaurant = new Restaurant("testRest", "testDescription");
        restaurantService.addNewRestaurant(restaurant);
        assertEquals("testDescription", restaurantService.getDescriptionByName("testRest"));
    }

    @Test
    void getDescriptionByName() {
        String testName = "testName";
        String testDescription = "testDescription";
        Restaurant test = new Restaurant(testName, testDescription);
        restaurantService.addNewRestaurant(test);
        String description = restaurantService.getDescriptionByName(testName);
        assertEquals(testDescription, description);
    }

    @Test
    void getAllRestaurants() {
        Restaurant restaurant = new Restaurant("testName", "testDescription");
        restaurantService.addNewRestaurant(restaurant);
        List<Restaurant> all = restaurantService.getAllRestaurants();
        assertEquals(all.get(0).getId(), 1);
    }

    @Test
    public void changeDescriptionByName() {
        String testName = "testName";
        restaurantService.changeDescriptionByName(testName, "newTestDescription");
        String description = restaurantService.getDescriptionByName(testName);
        assertEquals("newTestDescription", description);
    }
}
