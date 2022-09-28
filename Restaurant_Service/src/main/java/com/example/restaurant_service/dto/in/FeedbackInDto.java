package com.example.restaurant_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Dto to create/update feedback")
public class FeedbackInDto {

    @Schema(description = "Needs if feedback not exists")
    private Long id;
    @Min(1)
    @Schema(description = "Cannot be 0." +
            "Provided restaurant should be exist")
    private Long restaurantId;

    @Schema(description = "Feedback text")
    private String feedback;

    @Min(1)
    @Max(5)
    @Schema(description = "Value from 1 to 5")
    private Integer rating;

}
