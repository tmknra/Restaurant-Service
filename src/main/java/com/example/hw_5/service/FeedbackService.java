package com.example.hw_5.service;

import com.example.hw_5.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllByRestaurantID(Integer id);
    Double getAverageRatingByRestaurantID(Integer restaurantID);
    String getFeedbackTextByID(Integer id);

    void addNewFeedback(Feedback feedback);
    void changeFeedbackByID(Integer feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackByRestaurantId(Integer id);
}
