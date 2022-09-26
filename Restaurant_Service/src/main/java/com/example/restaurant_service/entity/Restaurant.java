package com.example.restaurant_service.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String phone_number;

    @Column
    private String email_address;

    @OneToMany(mappedBy = "restaurant",
                cascade = CascadeType.PERSIST,
                fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Feedback> feedbacks;

    @Column
    private LocalDate foundation_date;

}
