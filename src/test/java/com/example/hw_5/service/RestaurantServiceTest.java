package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends Hw5ApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    private Long id;

    @BeforeAll
    void setUp() throws FoundationDateIsExpiredException {
        Restaurant restaurant = new Restaurant("testRest", "testDescription", "", "", "22/03/2020");
        restaurantService.addNewRestaurant(restaurant);
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
        id = allRestaurants.get(allRestaurants.size() - 1).getId();
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
    void changeDescriptionByName() {
        String testName = "testRest";
        restaurantService.changeDescriptionByName(testName, "newTestDescription");
        String description = restaurantService.getDescriptionByName(testName);
        assertEquals("newTestDescription", description);
    }

    @Test
    void setFoundationDate() throws FoundationDateIsExpiredException {
        String instantExpected = "11/02/2001";

        MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class, CALLS_REAL_METHODS);
        LocalDate parse = LocalDate.parse(instantExpected, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mockedStatic.when(LocalDate::now).thenReturn(parse);

        assertThrowsExactly(FoundationDateIsExpiredException.class, () ->  restaurantService.addNewRestaurant(
                new Restaurant("testRest", "testDescription", "", "", "05/08/2022")));

        restaurantService.setFoundationDateById(id, instantExpected);
        String foundationDateById = restaurantService.getFoundationDateById(id);
        assertEquals(instantExpected, foundationDateById);
    }

    @AfterAll
    void clear() {
        restaurantService.deleteRestaurantByName("testRest");
    }
}
