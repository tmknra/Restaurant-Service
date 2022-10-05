package com.example.restaurant_service.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO to represent restaurant shortcut list from database")
public class RestaurantSmallOutDto {
    private Long id;
    private String name;
    private BigDecimal average;
}
