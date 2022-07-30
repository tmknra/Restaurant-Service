package com.example.hw_5.dao;

import com.example.hw_5.entity.Feedback;

import java.util.List;

public interface FeedbackDao {
    List<Feedback> getAllByRestaurantID(Integer id);
    Double getAverageRatingByRestaurantID(Integer restaurantID);
    String getFeedbackTextByID(Integer id);

    void addNewFeedback(Feedback feedback);
    void changeFeedbackByID(Integer feedbackID, String newFeedback, Integer newRating);
}
