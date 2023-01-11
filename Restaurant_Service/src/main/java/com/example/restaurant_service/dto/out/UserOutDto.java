package com.example.restaurant_service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@Schema(description = "DTO to represent user from database")
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private Long id;
    private String lastname;
    private String name;
    private String patronymic;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @EqualsAndHashCode.Exclude
    @Schema(description = "Default value - current time when register new user")
    private LocalDateTime registrationDate;
}
