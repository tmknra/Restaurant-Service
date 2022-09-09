package com.example.user_service.dto.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleInDto {
    @NotBlank
    private String name;

}
