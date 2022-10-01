package com.example.restaurant_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Feedback entity from database")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurantId;

    @Basic
    @Column
    private String feedback;
    @Basic
    @Column
    private Integer rating;
}

