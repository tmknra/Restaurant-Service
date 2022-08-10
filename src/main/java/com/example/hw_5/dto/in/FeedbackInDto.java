package com.example.hw_5.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
@Getter
public class FeedbackInDto {

    private Long id;
    private Long restaurantid;
    private String feedback;
    private Integer rating;

}
