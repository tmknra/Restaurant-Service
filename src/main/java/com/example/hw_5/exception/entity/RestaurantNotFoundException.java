package com.example.hw_5.exception.entity;

public class RestaurantNotFoundException extends Exception {
    @Override
    public String toString() {
        return "Wrong restaurant id. No such restaurant exist.";
    }
}
