package com.example.restaurant_service.exception.entity;

public class RestaurantNotFoundException extends Exception {
    @Override
    public String toString() {
        return "Wrong restaurant id. No such restaurant exist.";
    }
}
