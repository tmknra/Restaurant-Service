package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(description = "DTO to create/update user")
public class UserInDto {

    @Schema(description = "User's lastname")
    private String lastname;

    @NotEmpty
    @Schema(description = "User's firstname")
    private String name;
    @Schema(description = "User's patronymic")
    private String patronymic;

    @Email
    @Schema(description = "User's email. Must be unique.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$",
            message = "Invalid password")
    private String password;

}
