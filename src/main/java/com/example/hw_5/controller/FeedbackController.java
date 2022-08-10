package com.example.hw_5.controller;

import com.example.hw_5.dto.in.FeedbackInDto;
import com.example.hw_5.dto.out.FeedbackOutDto;
import com.example.hw_5.entity.Feedback;
import com.example.hw_5.mapper.FeedbackMapper;
import com.example.hw_5.service.FeedbackService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, FeedbackMapper feedbackMapper) {
        this.feedbackService = feedbackService;
        this.feedbackMapper = feedbackMapper;
    }

    @GetMapping("/get/{id}")
    public FeedbackOutDto getFeedbackById(@PathVariable Long id){
        Feedback feedback = feedbackService.getFeedback(id);
        return feedbackMapper.feedbackToFeedbackOutDto(feedback);
    }

    @GetMapping("/all/{restID}")
    public List<FeedbackOutDto> getFeedbacks(@PathVariable Long restID){
        return feedbackService.getAllByRestaurantId(restID);
    }

    @GetMapping("/get_rating/{id}")
    public Double getAverageRatingByRestaurantID(@PathVariable Long id){
       return feedbackService.getAverageRatingByRestaurantID(id);
    }

    @GetMapping("/{id}")
    public String getFeedbackTextByID(@PathVariable Long id){
        return feedbackService.getFeedbackTextByID(id);
    }

    @PostMapping("/new")
    public Feedback addFeedbackByRestaurantID(@RequestBody FeedbackInDto feedback){
        return feedbackService.addNewFeedback(feedback);
    }

    @PostMapping("/change")
    public Feedback changeFeedbackByID(@RequestBody FeedbackInDto feedback){
        return feedbackService.changeFeedbackByID(feedback.getId(), feedback.getFeedback(), feedback.getRating());
    }

    @PostMapping("/delete")
    public void deleteFeedbackById(@RequestBody JsonNode id){
        feedbackService.deleteFeedbackById(id.get("id").asLong());
    }

    @PostMapping("/deleteAll/{restaurantId}")
    public void deleteAllByRestaurantId(@PathVariable Long restaurantId){
        feedbackService.deleteAllByRestaurantId(restaurantId);
    }
}
