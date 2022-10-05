package com.example.restaurant_service.dto.out;

import com.example.restaurant_service.entity.KitchenType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Schema(description = "DTO to represent restaurant from database")
public class RestaurantOutDto {

    private Long id;
    private String name;
    private String description;
    private KitchenType kitchenType;
    private String phone_number;
    private String email_address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundation_date;

}
