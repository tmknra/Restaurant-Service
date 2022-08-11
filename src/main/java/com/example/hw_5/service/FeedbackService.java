package com.example.hw_5.service;

import com.example.hw_5.dto.in.FeedbackInDto;
import com.example.hw_5.dto.out.FeedbackOutDto;
import com.example.hw_5.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedback(Long id);

    List<FeedbackOutDto> getAllByRestaurantId(Long restaurantId);
    Double getAverageRatingByRestaurantID(Long restaurantID);
    String getFeedbackTextByID(Long id);

    Feedback addNewFeedback(FeedbackInDto feedback);
    Feedback changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackById(Long id);
    void deleteAllByRestaurantId(Long restaurantId);

}
