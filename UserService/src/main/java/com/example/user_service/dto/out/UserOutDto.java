package com.example.user_service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserOutDto {

    private Long id;
    private String lastname;
    private String name;
    private String patronymic;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    // @EqualsAndHashCode.Exclude
    private LocalDateTime registrationDate;
    private List<RoleOutDto> role;
}