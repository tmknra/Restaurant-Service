package com.example.restaurant_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "update restaurants set is_deleted = true where id=?")
@Where(clause = "is_deleted=false")
@Schema(description = "Restaurant entity from database")
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

    @OneToMany(mappedBy = "restaurantId",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Feedback> feedbacks;

    @CreationTimestamp
    @Column
    private LocalDate foundation_date;

    @UpdateTimestamp
    @Column(name = "update_datetime")
    private LocalDate updateDatetime;

    @Column(name = "is_deleted")
    @ColumnDefault(value = "false")
    private Boolean isDeleted;

    @Column(name = "kitchen_type")
    @Enumerated(value = EnumType.STRING)
    private KitchenType kitchenType;
}
