package com.example.restaurant_service.service;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends RestaurantServiceAppTests {

    @Autowired
    private RestaurantService restaurantService;

    private RestaurantOutDto testRestaurant;

    // @BeforeAll
    // void setUp() throws FoundationDateIsExpiredException, NumberParseException, PhoneNumberNotRuException {
    //     RestaurantInDto restaurantInDto = new RestaurantInDto("testName",
    //             "testDescription",
    //             "+79991122333",
    //             "",
    //             LocalDate.of(2022, 3, 2)
    //     );
    //     testRestaurant = restaurantService.createRestaurant(restaurantInDto);
    // }

    @Test
    void getRestaurant() throws RestaurantNotFoundException {
        RestaurantOutDto restaurant = restaurantService.getRestaurant(this.testRestaurant.getId());
        assertEquals(testRestaurant.getId(), restaurant.getId());
    }

    // @Test
    // void getAllRestaurants() {
    //     String testName = "testName";
    //     List<Restaurant> restaurants = restaurantService.getAllRestaurants(Pageable.unpaged()).stream().toList();
    //     String targetName = restaurants.get(restaurants.size() - 1).getName();
    //     assertEquals(testName, targetName);
    // }
    //
    // @Test
    // void setFoundationDate() throws FoundationDateIsExpiredException, RestaurantNotFoundException {
    //     LocalDate instantExpected = LocalDate.of(2011, 2, 11);
    //
    //     MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class, CALLS_REAL_METHODS);
    //     mockedStatic.when(LocalDate::now).thenReturn(instantExpected);
    //
    //     assertThrowsExactly(FoundationDateIsExpiredException.class, () ->
    //             restaurantService.createRestaurant(
    //                     new RestaurantInDto("qwe", "zxc", "+79991111222", "asd@qwe.rt",
    //                             LocalDate.of(2023, 1, 1))));
    //
    //     assertThrowsExactly(FoundationDateIsExpiredException.class, () ->
    //             restaurantService.setFoundationDateById(
    //                     testRestaurant.getId(), LocalDate.of(2023, 1, 1)
    //             ));
    //
    //     restaurantService.setFoundationDateById(testRestaurant.getId(), instantExpected);
    //     LocalDate foundationDateById = restaurantService.getFoundationDateById(testRestaurant.getId());
    //     assertEquals(instantExpected, foundationDateById);
    // }

}
