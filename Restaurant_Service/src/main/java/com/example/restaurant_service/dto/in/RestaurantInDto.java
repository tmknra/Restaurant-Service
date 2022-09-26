package com.example.restaurant_service.dto.in;

import com.example.restaurant_service.validation.constraint.EmailValidation;
import com.example.restaurant_service.validation.constraint.PhoneNumberValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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

    @NotBlank
    private Long ownerId;
    @PhoneNumberValidation
    private String phone_number;

    @Nullable
    @EmailValidation
    private String email_address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate foundation_date;

}
