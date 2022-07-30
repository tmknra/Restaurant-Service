package com.example.hw_5.service.impl;

import com.example.hw_5.dao.FeedbackDao;
import com.example.hw_5.entity.Feedback;
import com.example.hw_5.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public List<Feedback> getAllByRestaurantID(Integer id) {
        return feedbackDao.getAllByRestaurantID(id);
    }

    @Override
    public Double getAverageRatingByRestaurantID(Integer restaurantID) {
        return feedbackDao.getAverageRatingByRestaurantID(restaurantID);
    }

    @Override
    public String getFeedbackTextByID(Integer id) {
        return feedbackDao.getFeedbackTextByID(id);
    }

    @Override
    public void addNewFeedback(Feedback feedback) {
        feedbackDao.addNewFeedback(feedback);
    }

    @Override
    public void changeFeedbackByID(Integer feedbackID, String newFeedback, Integer newRating) {
        feedbackDao.changeFeedbackByID(feedbackID, newFeedback, newRating);
    }
}
