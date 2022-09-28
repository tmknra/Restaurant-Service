package com.example.restaurant_service.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public FeedbackOutDto addNewFeedback(Long restaurantId, String feedback, Integer rating) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(restaurantId);
        if (byId.isEmpty())
            throw new RestaurantNotFoundException();
        Restaurant restaurantEntity = byId.get();
        Feedback feedbackEntity = new Feedback(restaurantEntity, feedback, rating);
        return feedbackMapper.feedbackToFeedbackOutDto(feedbackRepository.save(feedbackEntity));
    }

    @Override
    public FeedbackOutDto getFeedback(Long id) throws FeedbackNotFoundException {
        return feedbackMapper.feedbackToFeedbackOutDto(getFeedbackById(id));
    }

    @Override
    public Page<FeedbackOutDto> getAllByRestaurantId(Pageable pageable, Long restaurantId) {
        return feedbackRepository.findAllByRestaurantId(pageable, restaurantId).map(feedbackMapper::feedbackToFeedbackOutDto);
    }

    @Override
    public Double getAverageRatingByRestaurantID(Long restaurantID) {
        return feedbackRepository.findAverageRatingByRestaurantid(restaurantID);
    }

    @Override
    @Transactional
    public FeedbackOutDto updateFeedbackById(Long feedbackID, String newFeedback, Integer newRating) {
        Feedback feedback = feedbackRepository.findById(feedbackID).get();
        feedback.setFeedback(newFeedback);
        feedback.setRating(newRating);
        return feedbackMapper.feedbackToFeedbackOutDto(feedback);
    }

    @Override
    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }
    //
    // @Override
    // public void deleteAllByRestaurantId(Long restaurantId) {
    //     feedbackRepository.deleteAll(feedbackRepository.findAllByRestaurantId(restaurantId));
    // }

    private Feedback getFeedbackById(Long id) throws FeedbackNotFoundException {
        Optional<Feedback> byId = feedbackRepository.findById(id);
        if (byId.isEmpty()){
            throw new FeedbackNotFoundException("Feedback not found.");
        }
        return byId.get();
    }
}
