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
    @JoinColumn(name = "restaurantid", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    @Basic
    @Column
    private String feedback;
    @Basic
    @Column
    private Integer rating;

    public Feedback(Restaurant restaurant, String feedback, Integer rating) {
        this.restaurant = restaurant;
        this.feedback = feedback;
        this.rating = rating;
    }

}

