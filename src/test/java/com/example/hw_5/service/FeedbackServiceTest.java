package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.dto.in.FeedbackInDto;
import com.example.hw_5.dto.out.FeedbackOutDto;
import com.example.hw_5.entity.Feedback;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedbackServiceTest extends Hw5ApplicationTests {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private RestaurantService restaurantService;

    private Long testRestId;
    private Long testFeedbackId;

    @BeforeAll
    void setUp() {
        testRestId = restaurantService.createRestaurantByName("testName");

        FeedbackInDto testFeedback1 = FeedbackInDto.builder()
                .restaurantid(testRestId)
                .feedback("testFeedback1")
                .rating(3)
                .build();

        FeedbackInDto testFeedback2 = FeedbackInDto.builder()
                .restaurantid(testRestId)
                .feedback("testFeedback2")
                .rating(4)
                .build();

        FeedbackInDto testFeedback3 = FeedbackInDto.builder()
                .restaurantid(testRestId)
                .feedback("testFeedback3")
                .rating(5)
                .build();

        Feedback feedback1 = feedbackService.addNewFeedback(testFeedback1);
        testFeedbackId = feedback1.getId();
        feedbackService.addNewFeedback(testFeedback2);
        feedbackService.addNewFeedback(testFeedback3);
    }

    @Test
    void getAllByRestaurantID() {
        List<FeedbackOutDto> allByRestaurantId = feedbackService.getAllByRestaurantId(testRestId);
        for (FeedbackOutDto feedbackOutDto : allByRestaurantId) {
            assertEquals(testRestId, feedbackOutDto.getRestaurantid());
        }
    }

    @Test
    void getAverageRatingByRestaurantID() {
        Double expectedAverageRating = 4.0;
        Double averageRating = feedbackService.getAverageRatingByRestaurantID(testRestId);
        assertEquals(expectedAverageRating, averageRating);
    }

    @Test
    void getFeedbackTextByID() {
        String feedbackTextByID = feedbackService.getFeedbackTextByID(testFeedbackId);
        assertEquals("testFeedback1", feedbackTextByID);
    }

    @Test
    void changeFeedbackByID() {
        String testFeed = "qqq";
        Integer testRating = 3;
        feedbackService.changeFeedbackByID(testFeedbackId, testFeed, testRating);
        Feedback feedback = feedbackService.getFeedback(testFeedbackId);

        assertEquals(testFeed, feedback.getFeedback());
        assertEquals(testRating, feedback.getRating());
    }

}
