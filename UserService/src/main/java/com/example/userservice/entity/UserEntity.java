package com.example.userservice.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String lastname;

    @Column
    private String name;

    @Column
    private String patronymic;

    @Column
    private String email;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

}
