package com.example.restaurant_service.dto.in;

import com.example.restaurant_service.entity.KitchenType;
import com.example.restaurant_service.validation.constraint.EmailValidation;
import com.example.restaurant_service.validation.constraint.PhoneNumberValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO to create/update restaurant")
public class RestaurantInDto {

    @NotBlank(message = "Can not be empty.")
    @Schema(description = "Restaurant name. Can be not unique.")
    private String name;

    @Schema(description = "Text description.")
    private String description;

    @NotBlank
    @Enumerated(value = EnumType.STRING)
    private KitchenType kitchenType;

    @Min(1)
    @Schema(description = "Owners id from user repository, can be null.")
    private Long ownerId;

    @PhoneNumberValidation
    private String phone_number;

    @Nullable
    @EmailValidation
    private String email_address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Opening date. Cannot be more than the current date.")
    private final LocalDate foundation_date;

}
