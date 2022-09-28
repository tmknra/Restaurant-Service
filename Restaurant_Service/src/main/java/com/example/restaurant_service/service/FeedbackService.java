package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    FeedbackOutDto getFeedback(Long id) throws FeedbackNotFoundException;

    Page<FeedbackOutDto> getAllByRestaurantId(Pageable pageable, Long restaurantId);
    Double getAverageRatingByRestaurantID(Long restaurantID);
    // String getFeedbackTextByID(Long id) throws FeedbackNotFoundException;

    FeedbackOutDto addNewFeedback(Long restaurantId, String feedback, Integer rating) throws RestaurantNotFoundException;
    FeedbackOutDto updateFeedbackById(Long feedbackID, String newFeedback, Integer newRating);

    void deleteFeedbackById(Long id);
    // void deleteAllByRestaurantId(Long restaurantId);

}
