package com.example.restaurant_service.controller.impl;

import com.example.restaurant_service.controller.FeedbackController;
import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.FeedbackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackControllerImpl implements FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackControllerImpl(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New feedback successfully created."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid rating field."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    @Override
    public FeedbackOutDto addFeedbackByRestaurantId(FeedbackInDto feedbackInDto) throws RestaurantNotFoundException {
        return feedbackService.addNewFeedback(feedbackInDto);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns feedback."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided feedback not found.")})
    @Override
    public FeedbackOutDto getFeedbackById(Long id) throws FeedbackNotFoundException {
        return feedbackService.getFeedback(id);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback successfully updated."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided feedback/restaurant not found.")})
    @Override
    public FeedbackOutDto updateFeedbackById(Long id, FeedbackInDto feedback) throws FeedbackNotFoundException, RestaurantNotFoundException {
        return feedbackService.updateFeedbackById(id, feedback);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback successfully deleted."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Provided feedback not found.")})
    @Override
    public ResponseEntity<?> deleteFeedbackById(Long id) throws FeedbackNotFoundException {
        return feedbackService.deleteFeedbackById(id);
    }
}
