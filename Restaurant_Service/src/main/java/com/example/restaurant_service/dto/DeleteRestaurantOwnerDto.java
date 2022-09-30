package com.example.restaurant_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO to change deleted user from restaurant_service to new user by rabbit messaging")
public class DeleteRestaurantOwnerDto {

    private Long ownerId;

}
