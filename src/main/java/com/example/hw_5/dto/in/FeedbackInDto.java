package com.example.hw_5.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@Getter
public class FeedbackInDto {

    private Long id;

    @Min(1)
    private Long restaurantid;

    private String feedback;

    @Min(1)
    @Max(5)
    private Integer rating;

}
