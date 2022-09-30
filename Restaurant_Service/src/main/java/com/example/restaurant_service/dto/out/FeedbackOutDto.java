package com.example.restaurant_service.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Schema(description = "DTO to represent feedback from database")
public class FeedbackOutDto {

    private Long id;
    private Long restaurantId;
    private String feedback;
    private Integer rating;

}
