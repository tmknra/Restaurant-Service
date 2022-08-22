package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedback(Long id);

    Page<Feedback> getAllByRestaurantId(Pageable pageable, Long restaurantId);
    Double getAverageRatingByRestaurantID(Long restaurantID);
    String getFeedbackTextByID(Long id);

    FeedbackOutDto addNewFeedback(Long restaurantId, String feedback, Integer rating) throws RestaurantNotFoundException;
    Feedback changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackById(Long id);
    void deleteAllByRestaurantId(Long restaurantId);

}
