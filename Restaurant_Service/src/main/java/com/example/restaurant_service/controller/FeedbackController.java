package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/feedbacks")
public interface FeedbackController {

    @PostMapping
    @Operation(summary = "Creates new feedback by restaurant id")
    FeedbackOutDto addFeedbackByRestaurantId(@RequestBody @Valid FeedbackInDto feedback) throws RestaurantNotFoundException;

    @GetMapping("/{id}")
    @Operation(summary = "Returns feedback by id")
    FeedbackOutDto getFeedbackById(@PathVariable Long id) throws FeedbackNotFoundException;

    @PutMapping("/{id}")
    @Operation(summary = "Updates provided feedback")
    FeedbackOutDto updateFeedbackById(@PathVariable Long id, @RequestBody @Valid FeedbackInDto feedback) throws FeedbackNotFoundException, RestaurantNotFoundException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete provided feedback")
    ResponseEntity<?> deleteFeedbackById(@PathVariable Long id) throws FeedbackNotFoundException;


}
