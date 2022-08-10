package com.example.hw_5.service;

import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import com.google.i18n.phonenumbers.NumberParseException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {
    long createRestaurantByName(String name);
    long createRestaurantByNameAndDate(String name, LocalDate foundationDate) throws FoundationDateIsExpiredException;
    long createRestaurantByNameAndPhoneNumber(String name, String phoneNumber) throws NumberParseException;

    Restaurant getRestaurant(Long id);
    List<RestaurantOutDto> getAllRestaurants();

    LocalDate getFoundationDateById(Long id);

    Restaurant createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException;

    void setEmailById(Long id, String email);
    void setPhoneNumberById(Long id, String number) throws NumberParseException;
    void setFoundationDateById(Long id, LocalDate date) throws FoundationDateIsExpiredException;

    void deleteRestaurantByName(String name);
}