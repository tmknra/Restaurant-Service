package com.example.hw_5.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class FeedbackOutDto {

    private Long id;
    private Long restaurantid;
    private String feedback;
    private Integer rating;

}
