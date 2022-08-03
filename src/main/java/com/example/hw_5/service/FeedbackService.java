package com.example.hw_5.service;

import com.example.hw_5.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllByRestaurantID(Long id);
    Double getAverageRatingByRestaurantID(Long restaurantID);
    String getFeedbackTextByID(Long id);

    void addNewFeedback(Feedback feedback);
    void changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackByRestaurantId(Long id);
}
