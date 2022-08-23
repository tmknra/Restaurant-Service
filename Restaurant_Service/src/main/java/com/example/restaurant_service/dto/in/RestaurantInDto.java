package com.example.restaurant_service.dto.in;

import com.example.restaurant_service.validation.constraint.EmailValidation;
import com.example.restaurant_service.validation.constraint.PhoneNumberValidation;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RestaurantInDto {

    @NotBlank(message = "Can not be empty.")
    private String name;
    private String description;

    @PhoneNumberValidation
    private String phone_number;

    @Nullable
    @EmailValidation
    private String email_address;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate foundation_date;

}
