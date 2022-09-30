package com.example.restaurant_service.service;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedbackServiceTest extends RestaurantServiceAppTests {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private RestaurantService restaurantService;

    private Long testRestId;
    private Long testFeedbackId;

    // @BeforeAll
    // void setUp() throws RestaurantNotFoundException {
    //     testRestId = restaurantService.createRestaurantByName("testName");
    //
    //     FeedbackInDto testFeedback1 = FeedbackInDto.builder()
    //             .restaurantid(testRestId)
    //             .feedback("testFeedback1")
    //             .rating(3)
    //             .build();
    //
    //     FeedbackInDto testFeedback2 = FeedbackInDto.builder()
    //             .restaurantid(testRestId)
    //             .feedback("testFeedback2")
    //             .rating(4)
    //             .build();
    //
    //     FeedbackInDto testFeedback3 = FeedbackInDto.builder()
    //             .restaurantid(testRestId)
    //             .feedback("testFeedback3")
    //             .rating(5)
    //             .build();
    //
    //     FeedbackOutDto feedback1 = feedbackService.addNewFeedback(testFeedback1.getRestaurantid(), testFeedback1.getFeedback(), testFeedback1.getRating());
    //     testFeedbackId = feedback1.getId();
    //     feedbackService.addNewFeedback(testFeedback2.getRestaurantid(), testFeedback2.getFeedback(), testFeedback2.getRating());
    //     feedbackService.addNewFeedback(testFeedback3.getRestaurantid(), testFeedback3.getFeedback(), testFeedback3.getRating());
    // }

    // @Test
    // void getAllByRestaurantID() {
    //     List<String> allByRestaurantId = feedbackService.getAllByRestaurantId(Pageable.unpaged(), testRestId)
    //             .stream()
    //             .map(Feedback::getFeedback).toList();
    //     assertEquals("testFeedback1", allByRestaurantId.get(0));
    //     assertEquals("testFeedback2", allByRestaurantId.get(1));
    //     assertEquals("testFeedback3", allByRestaurantId.get(2));
    // }

    @Test
    void getAverageRatingByRestaurantID() throws RestaurantNotFoundException {
        Double expectedAverageRating = 4.0;
        ResponseEntity<?> averageRatingByRestaurantID = feedbackService.getAverageRatingByRestaurantId(testRestId);
        // assertEquals(expectedAverageRating, averageRating);
    }

    // @Test
    // void getFeedbackTextByID() {
    //     String feedbackTextByID = feedbackService.getFeedbackTextByID(testFeedbackId);
    //     assertEquals("testFeedback1", feedbackTextByID);
    // }

    // @Test
    // void changeFeedbackByID() {
    //     String testFeed = "qqq";
    //     Integer testRating = 3;
    //     feedbackService.changeFeedbackByID(testFeedbackId, testFeed, testRating);
    //     Feedback feedback = feedbackService.getFeedback(testFeedbackId);
    //
    //     assertEquals(testFeed, feedback.getFeedback());
    //     assertEquals(testRating, feedback.getRating());
    // }

}
