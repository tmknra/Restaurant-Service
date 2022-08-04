package com.example.hw_5.exception;

import java.time.LocalDate;

public class FoundationDateIsExpiredException extends Exception {
    private final String restaurantName;
    private final String foundationDate;
    public FoundationDateIsExpiredException(String name, String foundationDate) {
        this.restaurantName = name;
        this.foundationDate = foundationDate;
    }

    @Override
    public String toString() {
        return "Restaurant with name \"" + restaurantName + " \"" +
                "has foundation date " + foundationDate;
    }
}
