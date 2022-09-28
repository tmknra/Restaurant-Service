package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @Operation(summary = "Creates new feedback by restaurant id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New feedback successfully created."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    public FeedbackOutDto addFeedbackByRestaurantID(@RequestBody @Valid FeedbackInDto feedback) throws RestaurantNotFoundException {
        return feedbackService.addNewFeedback(feedback.getRestaurantId(), feedback.getFeedback(), feedback.getRating());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns feedback by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns feedback."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided feedback not found.")})
    public FeedbackOutDto getFeedbackById(@PathVariable Long id) throws FeedbackNotFoundException {
        return feedbackService.getFeedback(id);
    }

    @GetMapping("/all/{restID}")
    @Operation(summary = "Returns feedback list by restaurant id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns feedback list."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    public Page<FeedbackOutDto> getFeedbacks(Pageable pageable, @PathVariable Long restID) {
        return feedbackService.getAllByRestaurantId(pageable, restID);
    }

    @GetMapping("/get_rating/{id}")
    @Operation(summary = "Returns average restaurant rating.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns rating."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    public Double getAverageRatingByRestaurantID(@PathVariable Long id) {
        return feedbackService.getAverageRatingByRestaurantID(id);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Updates provided feedback")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback successfully updated."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided feedback not found.")})
    public FeedbackOutDto updateFeedbackById(@PathVariable Long id, @RequestBody @Valid FeedbackInDto feedback) {
        return feedbackService.updateFeedbackById(id, feedback.getFeedback(), feedback.getRating());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Creates new restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback successfully updated."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Provided feedback not found.")})
    public void deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
    }
}
