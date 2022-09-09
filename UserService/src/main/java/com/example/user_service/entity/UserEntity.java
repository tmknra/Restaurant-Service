package com.example.user_service.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @CreationTimestamp
    private LocalDateTime registrationDate;

    @Column
    private String password;

    @ManyToMany(
            mappedBy = "user",
            fetch= FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    private List<RoleEntity> role;

    @PrePersist
    public void saveDefaultPass(){
        if (password==null)
            password = "123456";
    }
}
