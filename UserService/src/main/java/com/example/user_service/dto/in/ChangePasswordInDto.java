package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(description = "DTO to change user password")
public class ChangePasswordInDto {

    @Schema(description = "User email")
    @NotBlank(message = "Can not be empty")
    @Email
    private String email;

    @Schema(description = "User's old password")
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String oldPassword;

    @Schema(description = "User's new password")
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String newPassword;

}
