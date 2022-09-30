package com.example.restaurant_service.dto.out;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantSmallOutDto {
    private Long id;
    private String name;
    private BigDecimal average;
}
