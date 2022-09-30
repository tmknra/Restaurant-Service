package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<?> getFeedback(Long id) throws FeedbackNotFoundException;

    Page<FeedbackOutDto> getAllByRestaurantId(Pageable pageable, Long restaurantId) throws RestaurantNotFoundException;
    ResponseEntity<?> getAverageRatingByRestaurantId(Long restaurantID) throws RestaurantNotFoundException;

    ResponseEntity<?> addNewFeedback(FeedbackInDto feedbackInDto) throws RestaurantNotFoundException;
    ResponseEntity<?> updateFeedbackById(Long feedbackId, FeedbackInDto feedbackInDto) throws FeedbackNotFoundException, RestaurantNotFoundException;

    ResponseEntity<?> deleteFeedbackById(Long id) throws FeedbackNotFoundException;

}
