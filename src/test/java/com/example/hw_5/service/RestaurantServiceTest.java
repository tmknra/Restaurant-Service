package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends Hw5ApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    private Restaurant testRestaurant;

    @BeforeAll
    void setUp() throws FoundationDateIsExpiredException, NumberParseException {
        RestaurantInDto restaurantInDto = new RestaurantInDto("testName",
                "testDescription",
                "+79991122333",
                "",
                LocalDate.of(2022, 3, 2)
        );
        testRestaurant = restaurantService.createRestaurant(restaurantInDto);
    }

    // @Test
    // void createRestaurantByName(){
    //     String testName = "testName";
    //     long restaurantByName = restaurantService.createRestaurantByName(testName);
    //     assertEquals(testName, restaurantService.getRestaurant(restaurantByName).getName());
    // }
    //
    // @Test
    // void createRestaurantByNameAndDate() throws FoundationDateIsExpiredException {
    //     String testName = "testName";
    //     LocalDate testDate = LocalDate.of(2020, 3, 3);
    //
    //     long restaurantByNameAndDate = restaurantService.createRestaurantByNameAndDate(testName, testDate);
    //     assertEquals(testName, restaurantService.getRestaurant(restaurantByNameAndDate).getName());
    //     assertEquals(testDate, restaurantService.getRestaurant(restaurantByNameAndDate).getFoundation_date());
    // }
    //
    // @Test
    // void createRestaurantByNameAndPhoneNumber() throws NumberParseException {
    //     String testName = "testName";
    //     String testNumber = "+79991122333";
    //
    //     long restaurantByNameAndPhoneNumber = restaurantService.createRestaurantByNameAndPhoneNumber(testName, testNumber);
    //     assertEquals(testName, restaurantService.getRestaurant(restaurantByNameAndPhoneNumber).getName());
    //     assertEquals(testNumber, restaurantService.getRestaurant(restaurantByNameAndPhoneNumber).getPhone_number());
    // }



    @Test
    void getRestaurant(){
        Restaurant restaurant = restaurantService.getRestaurant(this.testRestaurant.getId());
        assertEquals(testRestaurant.getId(), restaurant.getId());
    }

    @Test
    void getAllRestaurants() {
        String testName = "testName";
        List<RestaurantOutDto> all = restaurantService.getAllRestaurants();
        String targetName = all.get(all.size() - 1).getName();
        assertEquals(testName, targetName);
    }

    @Test
    void setFoundationDate() throws FoundationDateIsExpiredException {
        LocalDate instantExpected = LocalDate.of(2011, 2, 11);

        MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class, CALLS_REAL_METHODS);
        mockedStatic.when(LocalDate::now).thenReturn(instantExpected);

        assertThrowsExactly(FoundationDateIsExpiredException.class, () ->
                restaurantService.createRestaurant(
                        new RestaurantInDto("qwe", "zxc", "+79991111222", "asd@qwe.rt",
                                LocalDate.of(2023, 1, 1))));

        assertThrowsExactly(FoundationDateIsExpiredException.class, () ->
                restaurantService.setFoundationDateById(
                        testRestaurant.getId(), LocalDate.of(2023, 1, 1)
                ));

        restaurantService.setFoundationDateById(testRestaurant.getId(), instantExpected);
        LocalDate foundationDateById = restaurantService.getFoundationDateById(testRestaurant.getId());
        assertEquals(instantExpected, foundationDateById);
    }

    @AfterAll
    void clear() {
        restaurantService.deleteRestaurantByName("testName");
    }
}
