package com.example.restaurant_service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RestaurantOutDto {

    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String phone_number;
    private String email_address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundation_date;
    private List<FeedbackOutDto> feedbacks;

}
