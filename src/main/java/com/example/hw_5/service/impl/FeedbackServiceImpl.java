package com.example.hw_5.service.impl;

import com.example.hw_5.dto.in.FeedbackInDto;
import com.example.hw_5.dto.out.FeedbackOutDto;
import com.example.hw_5.entity.Feedback;
import com.example.hw_5.mapper.FeedbackMapper;
import com.example.hw_5.repository.FeedbackRepository;
import com.example.hw_5.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public Feedback getFeedback(Long id) {
        return getFeedbackById(id);
    }

    @Override
    public List<FeedbackOutDto> getAllByRestaurantId(Long restaurantId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByRestaurantid(restaurantId);
        List<FeedbackOutDto> feedbackOutDtos = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            feedbackOutDtos.add(
                    feedbackMapper.feedbackToFeedbackOutDto(feedback)
            );
        }
        return feedbackOutDtos;
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
    public Feedback addNewFeedback(FeedbackInDto feedback) {
        Feedback feedbackEntity = feedbackMapper.feedbackInDtoToFeedback(feedback);
        return feedbackRepository.save(feedbackEntity);
    }

    @Override
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
        List<Feedback> allByRestaurantid = feedbackRepository.findAllByRestaurantid(restaurantId);
        for (Feedback feedback : allByRestaurantid) {
            feedbackRepository.deleteById(feedback.getId());
        }
    }

    private Feedback getFeedbackById(Long id) {
        Optional<Feedback> byId = feedbackRepository.findById(id);
        return byId.get();
    }
}
