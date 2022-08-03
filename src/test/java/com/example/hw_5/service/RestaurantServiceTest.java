package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.entity.Restaurant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends Hw5ApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    @BeforeAll
    void setUp() {
        Restaurant restaurant = new Restaurant("testRest", "testDescription", "", "");
        restaurantService.addNewRestaurant(restaurant);
    }

    @Test
    void getDescriptionByName() {
        String testName = "testRest";
        String testDescription = "testDescription";
        String description = restaurantService.getDescriptionByName(testName);
        assertEquals(testDescription, description);
    }

    @Test
    void getAllRestaurants() {
        String testName = "testRest";
        List<Restaurant> all = restaurantService.getAllRestaurants();
        String targetName = all.get(all.size() - 1).getName();
        assertEquals(testName, targetName);
    }

    @Test
    public void changeDescriptionByName() {
        String testName = "testRest";
        restaurantService.changeDescriptionByName(testName, "newTestDescription");
        String description = restaurantService.getDescriptionByName(testName);
        assertEquals("newTestDescription", description);
    }

    @AfterAll
    void clear(){
        restaurantService.deleteRestaurantByName("testRest");
    }
}
