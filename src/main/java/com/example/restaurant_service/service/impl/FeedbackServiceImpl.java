package com.example.restaurant_service.service.impl;

import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.FeedbackMapper;
import com.example.restaurant_service.repository.FeedbackRepository;
import com.example.restaurant_service.repository.RestaurantRepository;
import com.example.restaurant_service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Feedback getFeedback(Long id) {
        return getFeedbackById(id);
    }

    @Override
    public Page<Feedback> getAllByRestaurantId(Pageable pageable, Long restaurantId) {
        // feedbackRepository.findAllByRestaurantId(pageable);
        // feedbackRepository.findAll(pageable);
        return feedbackRepository.findAllByRestaurantId(pageable, restaurantId);
        // return feedbackRepository.findAllByRestaurantId(pageable, restaurantId)
        //         .stream().map(feedbackMapper::feedbackToFeedbackOutDto)
        //         .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingByRestaurantID(Long restaurantID) {
        return feedbackRepository.findAverageRatingByRestaurantid(restaurantID);
    }

    @Override
    public String getFeedbackTextByID(Long id) {
        return getFeedbackById(id).getFeedback();
    }

    @Override
    public FeedbackOutDto addNewFeedback(Long restaurantId, String feedback, Integer rating) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(restaurantId);
        if (byId.isEmpty())
            throw new RestaurantNotFoundException();
        Restaurant restaurantEntity = byId.get();
        Feedback feedbackEntity = new Feedback(restaurantEntity, feedback, rating);
        return feedbackMapper.feedbackToFeedbackOutDto(feedbackRepository.save(feedbackEntity));
    }

    @Override
    @Transactional
    public Feedback changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating) {
        Feedback feedback = feedbackRepository.findById(feedbackID).get();
        feedback.setFeedback(newFeedback);
        feedback.setRating(newRating);
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public void deleteAllByRestaurantId(Long restaurantId) {
        feedbackRepository.deleteAll(feedbackRepository.findAllByRestaurantId(restaurantId));
    }

    private Feedback getFeedbackById(Long id) {
        Optional<Feedback> byId = feedbackRepository.findById(id);
        return byId.get();
    }
}
