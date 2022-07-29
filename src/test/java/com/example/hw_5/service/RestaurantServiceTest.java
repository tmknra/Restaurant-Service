package com.example.hw_5.service;

import com.example.hw_5.AppContextTest;
import com.example.hw_5.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantServiceTest extends AppContextTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void addRestaurant() {
        Restaurant restaurant = new Restaurant("testRest", "testDescription");
        restaurantService.addRestaurant(restaurant);
        assertEquals("testDescription", restaurantService.getDescription("testRest"));
    }

    @Test
    void getDescription() {
        String testName = "testName";
        String testDescription = "testDescription";
        Restaurant test = new Restaurant(testName, testDescription);
        restaurantService.addRestaurant(test);
        String description = restaurantService.getDescription(testName);
        assertEquals(testDescription, description);
    }

    @Test
    void getAll() {
        Restaurant restaurant = new Restaurant("testName", "testDescription");
        restaurantService.addRestaurant(restaurant);
        List<Restaurant> all = restaurantService.getAll();
        assertEquals(all.get(0).getId(), 1);
    }

    @Test
    public void changeRestaurantDescription() {
        String testName = "testName";
        // String testDescription = "testDescription";
        restaurantService.changeRestaurantDescription(testName, "newTestDescription");
        String description = restaurantService.getDescription(testName);
        assertEquals("newTestDescription", description);
    }
}
