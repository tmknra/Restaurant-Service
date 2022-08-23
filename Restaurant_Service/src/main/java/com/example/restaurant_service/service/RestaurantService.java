package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RestaurantService {

    long createRestaurantByName(String name);
    long createRestaurantByNameAndDate(String name, LocalDate foundationDate) throws FoundationDateIsExpiredException;
    long createRestaurantByNameAndPhoneNumber(String name, String phoneNumber) throws NumberParseException, PhoneNumberNotRuException;

    Restaurant getRestaurant(Long id) throws RestaurantNotFoundException;
    Page<Restaurant> getAllRestaurants(Pageable pageable);

    LocalDate getFoundationDateById(Long id) throws RestaurantNotFoundException;

    Restaurant createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException;

    void updateDescriptionById(Long id, String newDescription) throws RestaurantNotFoundException;
    void setEmailById(Long id, String email) throws RestaurantNotFoundException;
    void setPhoneNumberById(Long id, String number) throws NumberParseException, RestaurantNotFoundException, PhoneNumberNotRuException;
    void setFoundationDateById(Long id, LocalDate date) throws FoundationDateIsExpiredException, RestaurantNotFoundException;

    void deleteRestaurantByName(String name);

}