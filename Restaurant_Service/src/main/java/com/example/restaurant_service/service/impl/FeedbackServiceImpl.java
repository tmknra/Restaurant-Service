package com.example.restaurant_service.service.impl;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.FeedbackMapper;
import com.example.restaurant_service.repository.FeedbackRepository;
import com.example.restaurant_service.repository.RestaurantRepository;
import com.example.restaurant_service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final RestaurantRepository restaurantRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackServiceImpl(RestaurantRepository restaurantRepository, FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.restaurantRepository = restaurantRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public FeedbackOutDto addNewFeedback(FeedbackInDto feedbackInDto) throws RestaurantNotFoundException {
        if (restaurantRepository.findById(feedbackInDto.getRestaurantId()).isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found!");
        }
        Feedback feedback = feedbackMapper.feedbackInDtoToFeedback(feedbackInDto);
        return feedbackMapper.feedbackToFeedbackOutDto(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackOutDto getFeedback(Long id) throws FeedbackNotFoundException {
        return feedbackMapper.feedbackToFeedbackOutDto(getFeedbackById(id));
    }

    @Override
    public Page<FeedbackOutDto> getAllByRestaurantId(Pageable pageable, Long restaurantId) throws RestaurantNotFoundException {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found!");
        }
        return feedbackRepository.findAllByRestaurantId(Restaurant.builder().id(restaurantId).build(), pageable)
                .map(feedbackMapper::feedbackToFeedbackOutDto);
    }

    @Override
    public ResponseEntity<?> getAverageRatingByRestaurantId(Long restaurantId) throws RestaurantNotFoundException {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found!");
        }
        BigDecimal averageRatingByRestaurantId = feedbackRepository
                .findAverageRatingByRestaurantId(Restaurant.builder().id(restaurantId).build());
        HashMap<String, BigDecimal> responseBody = new HashMap<>();
        responseBody.put("rating", averageRatingByRestaurantId);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    @Transactional
    public FeedbackOutDto updateFeedbackById(Long feedbackId, FeedbackInDto feedbackInDto)
            throws FeedbackNotFoundException, RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(feedbackInDto.getRestaurantId());
        if (byId.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found!");
        }
        Feedback feedback = getFeedbackById(feedbackId);
        feedback.setRestaurantId(Restaurant.builder().id(feedbackInDto.getRestaurantId()).build());
        feedback.setFeedback(feedbackInDto.getFeedback());
        feedback.setRating(feedbackInDto.getRating());
        return feedbackMapper.feedbackToFeedbackOutDto(feedback);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteFeedbackById(Long id) throws FeedbackNotFoundException {
        feedbackRepository.delete(getFeedbackById(id));
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "Feedback successfully deleted.");
        return ResponseEntity.ok(message);
    }

    private Feedback getFeedbackById(Long id) throws FeedbackNotFoundException {
        Optional<Feedback> byId = feedbackRepository.findById(id);
        if (byId.isEmpty()) {
            throw new FeedbackNotFoundException("Feedback not found.");
        }
        return byId.get();
    }
}
