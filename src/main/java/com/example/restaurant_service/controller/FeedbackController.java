package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.FeedbackMapper;
import com.example.restaurant_service.service.FeedbackService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Page<FeedbackOutDto> getFeedbacks(Pageable pageable, @PathVariable Long restID){
        return feedbackService.getAllByRestaurantId(pageable, restID).map(feedbackMapper::feedbackToFeedbackOutDto);
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
    public FeedbackOutDto addFeedbackByRestaurantID(@RequestBody @Valid FeedbackInDto feedback) throws RestaurantNotFoundException {
        return feedbackService.addNewFeedback(feedback.getRestaurantid(), feedback.getFeedback(), feedback.getRating());
    }

    @PostMapping("/change_feedback/{id}")
    public Feedback changeFeedbackByID(@PathVariable Long id, @RequestBody @Valid FeedbackInDto feedback){
        return feedbackService.changeFeedbackByID(id, feedback.getFeedback(), feedback.getRating());
    }

    @PostMapping("/delete")
    public void deleteFeedbackById(@RequestBody JsonNode id){
        feedbackService.deleteFeedbackById(id.get("id").asLong());
    }

    @PostMapping("/deleteAll/{restaurantId}")
    public void deleteAllByRestaurantId(@PathVariable Long restaurantId){
        feedbackService.deleteAllByRestaurantId(restaurantId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return errors;
    }
}
