package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedback(Long id);

    List<String> getAllByRestaurantId(Long restaurantId);
    Double getAverageRatingByRestaurantID(Long restaurantID);
    String getFeedbackTextByID(Long id);

    FeedbackOutDto addNewFeedback(Long restaurantId, String feedback, Integer rating) throws RestaurantNotFoundException;
    Feedback changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackById(Long id);
    void deleteAllByRestaurantId(Long restaurantId);

}
