package com.example.hw_5.controller;

import com.example.hw_5.entity.Feedback;
import com.example.hw_5.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @GetMapping("/{restID}")
    public List<Feedback> getFeedbacks(@PathVariable Long restID){
        return feedbackService.getAllByRestaurantID(restID);
    }

    @GetMapping("/get_rating/{id}")
    public Double getAverageRatingByRestaurantID(@PathVariable Long id){
       return feedbackService.getAverageRatingByRestaurantID(id);
    }

    @GetMapping("/{id}")
    public String getFeedbackTextByID(@PathVariable Long id){
        return feedbackService.getFeedbackTextByID(id);
    }

    @PutMapping("/new")
    public void addFeedbackByRestaurantID(@RequestBody Feedback feedback){
        feedbackService.addNewFeedback(feedback);
    }

    @PutMapping("/change")
    public void changeFeedbackByID(@RequestBody Feedback feedback){
        feedbackService.changeFeedbackByID(feedback.getId(), feedback.getFeedback(), feedback.getRating());
    }

    @PutMapping("/delete")
    public void deleteFeedbackByRestaurantId(@RequestBody Long id){
        feedbackService.deleteFeedbackByRestaurantId(id);
    }
}
