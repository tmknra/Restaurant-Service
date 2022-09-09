package com.example.user_service.dto.in;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
public class ChangePasswordInDto {

    @NotBlank(message = "Can not be empty")
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String newPassword;

}
