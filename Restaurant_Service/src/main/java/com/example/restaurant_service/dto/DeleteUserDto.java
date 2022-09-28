package com.example.restaurant_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO to update owner when current was deleted from users repository")
public class DeleteUserDto {

    private Long oldUserId;
    private Long newUserId;

}
