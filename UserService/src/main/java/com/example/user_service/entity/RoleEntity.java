package com.example.user_service.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private List<UserEntity> user;
}
