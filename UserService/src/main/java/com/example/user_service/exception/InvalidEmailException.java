package com.example.user_service.exception;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
