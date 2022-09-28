package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {


    RestaurantOutDto getRestaurant(Long id) throws RestaurantNotFoundException;
    Page<RestaurantOutDto> getAllRestaurants(Pageable pageable);
    List<Restaurant> getRestaurantsByOwnerId(Long id);


    RestaurantOutDto createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException;


    void deleteRestaurantById(Long id) throws RestaurantNotFoundException;

    void updateRestaurant(RestaurantInDto restaurantInDto, Long id) throws RestaurantNotFoundException;
}