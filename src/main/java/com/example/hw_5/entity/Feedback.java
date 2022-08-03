package com.example.hw_5.entity;

import java.util.Objects;

public class Feedback {
   private Long id;
   private Long restaurantID;
   private String feedback;
   private Integer rating;

    public Feedback() {
    }

    public Feedback(Long restaurantID, String feedback, Integer rating) {
        this.restaurantID = restaurantID;
        this.feedback = feedback;
        this.rating = rating;
    }

    public Feedback(Long id, Long restaurantID, String feedback, Integer rating) {
        this.id = id;
        this.restaurantID = restaurantID;
        this.feedback = feedback;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
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
        return Objects.equals(restaurantID, feedback1.restaurantID) && Objects.equals(feedback, feedback1.feedback) && Objects.equals(rating, feedback1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantID, feedback, rating);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "restaurantID=" + restaurantID +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                '}';
    }
}

