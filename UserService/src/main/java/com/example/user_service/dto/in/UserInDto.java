package com.example.user_service.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Builder
@Data
public class UserInDto {

    private String lastname;

    @NotEmpty
    private String name;
    private String patronymic;

    @Email
    private String email;

    // @JsonFormat(shape = JsonFormat.Shape.STRING)
    // // @EqualsAndHashCode.Exclude
    // private LocalDateTime registrationDate;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String password;

}
