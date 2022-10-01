package com.example.user_service.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "User entity from database")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "User id. Autoincrement in database.")
    private Long id;

    @Column
    @Schema(description = "User's lastname.")
    private String lastname;

    @Column
    @Schema(description = "User's firstname.")
    private String name;

    @Column
    @Schema(description = "User's patronymic.")
    private String patronymic;

    @Column
    @Schema(description = "User's email. Unique value.")
    private String email;

    @Column(name = "registration_date")
    @CreationTimestamp
    @Schema(description = "Creation timestamp.")
    private LocalDateTime registrationDate;

    // TODO: delete pass
    @Column
    private String password;

    @PrePersist
    public void saveDefaultPass(){
        if (password==null)
            password = "123456";
    }
}
