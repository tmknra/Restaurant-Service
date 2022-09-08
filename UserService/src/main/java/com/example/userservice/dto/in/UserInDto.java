package com.example.userservice.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInDto {

    private String lastname;
    private String name;
    private String patronymic;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    // @EqualsAndHashCode.Exclude
    private LocalDateTime registrationDate;

}
