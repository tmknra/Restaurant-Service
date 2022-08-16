package com.example.restaurant_service.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

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
    private List<Feedback> feedbacks;

    @Column
    private LocalDate foundation_date;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "phone_number = " + phone_number + ", " +
                "email_address = " + email_address + ", " +
                "foundation_date = " + foundation_date + ")";
    }
}
