package com.example.hw_5.service;

import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.entity.FoundationDateIsExpiredException;
import com.example.hw_5.exception.entity.PhoneNumberNotRuException;
import com.example.hw_5.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    long createRestaurantByName(String name);
    long createRestaurantByNameAndDate(String name, LocalDate foundationDate) throws FoundationDateIsExpiredException;
    long createRestaurantByNameAndPhoneNumber(String name, String phoneNumber) throws NumberParseException, PhoneNumberNotRuException;

    Restaurant getRestaurant(Long id) throws RestaurantNotFoundException;
    List<RestaurantOutDto> getAllRestaurants();

    LocalDate getFoundationDateById(Long id) throws RestaurantNotFoundException;

    Restaurant createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException;

    void setEmailById(Long id, String email) throws RestaurantNotFoundException;
    void setPhoneNumberById(Long id, String number) throws NumberParseException, RestaurantNotFoundException, PhoneNumberNotRuException;
    void setFoundationDateById(Long id, LocalDate date) throws FoundationDateIsExpiredException, RestaurantNotFoundException;

    void deleteRestaurantByName(String name);

}