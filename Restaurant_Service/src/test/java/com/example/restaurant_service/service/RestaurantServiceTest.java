package com.example.restaurant_service.service;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.dto.DeleteRestaurantOwnerDto;
import com.example.restaurant_service.dto.UpdateRestaurantOwnerDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.dto.out.RestaurantSmallOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.OwnerNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.RestaurantMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:test-application.properties")
class RestaurantServiceTest extends RestaurantServiceAppTests {

    @Autowired
    private RestaurantService restaurantService;
    private Long testRestaurantId1;
    private Long testRestaurantId2;

    @BeforeEach
    void setUp() throws NumberParseException, FoundationDateIsExpiredException, OwnerNotFoundException {
        RestaurantInDto test1 = RestaurantInDto.builder()
                .name("test1")
                .description("description1")
                .ownerId(1L)
                .build();
        RestaurantOutDto save1 = restaurantService.createRestaurant(test1);
        testRestaurantId1 = save1.getId();

        RestaurantInDto test2 = RestaurantInDto.builder()
                .name("test2")
                .description("description2")
                .build();
        RestaurantOutDto save2 = restaurantService.createRestaurant(test2);
        testRestaurantId2 = save2.getId();
    }

    @Test
    void getRestaurant() throws RestaurantNotFoundException {
        RestaurantOutDto restaurant = restaurantService.getRestaurant(testRestaurantId1);
        assertEquals("test1", restaurant.getName());
        assertEquals("description1", restaurant.getDescription());
    }

    @Test
    void getAllRestaurants() {
        Page<RestaurantOutDto> all = restaurantService.getAllRestaurants(Pageable.unpaged());
        assertTrue(all.getTotalElements() > 0);

        RestaurantOutDto restaurantOutDto = all.stream().toList().get((int) all.getTotalElements());
        assertEquals("test2", restaurantOutDto.getName());
        assertEquals("description2", restaurantOutDto.getDescription());
    }

    @Test
    void getRestaurantsByOwnerId() {
        Restaurant restaurant = restaurantService.getRestaurantsByOwnerId(1L).get(0);
        System.out.println(restaurant);
    }

    @Test
    void deleteRestaurantById() throws RestaurantNotFoundException {
        restaurantService.deleteRestaurantById(testRestaurantId2);
        assertThrowsExactly(RestaurantNotFoundException.class, (Executable) restaurantService.deleteRestaurantById(testRestaurantId2));
    }

    @Test
    void updateRestaurant() throws RestaurantNotFoundException, OwnerNotFoundException {
        RestaurantInDto update = RestaurantInDto.builder()
                .name("updatedName")
                .description("updatedDescription")
                .build();
        restaurantService.updateRestaurant(update, testRestaurantId1);
        RestaurantOutDto restaurant = restaurantService.getRestaurant(testRestaurantId1);
        assertEquals("updatedName", restaurant.getName());
        assertEquals("updatedDescription", restaurant.getDescription());
    }

    @Test
    void getSmallList() {
        assertTrue(0 < restaurantService.getSmallList(Pageable.unpaged()).getTotalElements());
        assertEquals("RestaurantSmallOutDto", restaurantService.getSmallList(Pageable.unpaged())
                .stream().toList().get(0).getClass().getName());
    }

    @Test
    void updateOwnerForRestaurants()  {
        UpdateRestaurantOwnerDto updateOwnerDto = UpdateRestaurantOwnerDto.builder()
                .oldUserId(1L)
                .newUserId(2L)
                .build();
        restaurantService.updateOwnerForRestaurants(updateOwnerDto);
        List<Restaurant> restaurantsByOwnerId = restaurantService.getRestaurantsByOwnerId(2L);
        Restaurant restaurant =
                restaurantsByOwnerId.stream().filter(r -> Objects.equals(r.getId(), testRestaurantId1)).toList().get(0);
        assertEquals(testRestaurantId1, restaurant.getId());
        assertEquals(2L, restaurant.getOwnerId());
    }

    @Test
    void deleteOwnerFromRestaurants() {
        DeleteRestaurantOwnerDto deleteOwnerDto =
                DeleteRestaurantOwnerDto.builder()
                .ownerId(2L)
                .build();
        restaurantService.deleteOwnerFromRestaurants(deleteOwnerDto);
    }
}