package com.example.userservice.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserOutDto {

    private Long id;
    private String lastname;
    private String name;
    private String patronymic;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    // @EqualsAndHashCode.Exclude
    private LocalDateTime registrationDate;

}
