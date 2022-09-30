package com.example.restaurant_service.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Feedback(Restaurant restaurantId, String feedback, Integer rating) {
        this.restaurantId = restaurantId;
        this.feedback = feedback;
        this.rating = rating;
    }

}

