package com.example.hw_5.dto.out;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class RestaurantOutDto {

    private Long id;
    private String name;
    private String description;
    private String phone_number;
    private String email_address;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate foundation_date;
}
