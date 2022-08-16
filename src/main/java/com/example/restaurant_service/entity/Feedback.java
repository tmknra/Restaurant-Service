package com.example.restaurant_service.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantid", nullable = false)
    private Restaurant restaurant;

    @Basic
    @Column
    private String feedback;
    @Basic
    @Column
    private Integer rating;

    public Feedback() {
    }

    public Feedback(Restaurant restaurant, String feedback, Integer rating) {
        this.restaurant = restaurant;
        this.feedback = feedback;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Feedback feedback1 = (Feedback) o;
        return Objects.equals(id, feedback1.id) && Objects.equals(restaurant, feedback1.restaurant) && Objects.equals(feedback, feedback1.feedback) && Objects.equals(rating, feedback1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurant, feedback, rating);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "feedback = " + feedback + ", " +
                "rating = " + rating + ")";
    }
}

