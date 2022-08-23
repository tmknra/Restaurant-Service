package com.example.restaurant_service.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class FeedbackOutDto {

    private Long id;
    private String feedback;
    private Integer rating;

}
