package com.example.hw_5.dto.in;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RestaurantInDto {

    private String name;
    private String description;
    private String phone_number;
    private String email_address;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate foundation_date;

}
