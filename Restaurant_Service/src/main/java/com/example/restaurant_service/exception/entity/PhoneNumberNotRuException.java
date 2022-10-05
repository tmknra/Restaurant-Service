package com.example.restaurant_service.exception.entity;

public class PhoneNumberNotRuException extends IllegalArgumentException {
    public PhoneNumberNotRuException(String message) {
        super(message);
    }
}
