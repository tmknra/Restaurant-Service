package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
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

    private Restaurant testRestaurant;
    private Feedback testFeedback;
    private Long testRestaurantId;
    private Long testFeedbackId;

    @BeforeAll
    void setUp() throws FoundationDateIsExpiredException {
        testRestaurant = new Restaurant("test", "test");
        restaurantService.addNewRestaurant(testRestaurant);
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
        testRestaurantId = allRestaurants.get(allRestaurants.size() - 1).getId();

        testFeedback = new Feedback(testRestaurantId, "testFeedback", 1);
        feedbackService.addNewFeedback(testFeedback);
        List<Feedback> allByRestaurantID = feedbackService.getAllByRestaurantID(testRestaurantId);
        testFeedbackId = allByRestaurantID.get(allByRestaurantID.size() - 1).getId();
    }

    @Test
    void getAllByRestaurantID() {
        List<Feedback> allByRestaurantID = feedbackService.getAllByRestaurantID(testRestaurantId);
        Feedback feedback = allByRestaurantID.get(0);
        assertEquals(testRestaurantId, feedback.getRestaurantID());
    }

    @Test
    void getAverageRatingByRestaurantID() {
        Double averageRating = feedbackService.getAverageRatingByRestaurantID(testRestaurantId);
        assertEquals(testFeedback.getRating().doubleValue(), averageRating);
    }

    @Test
    void getFeedbackTextByID() {
        String feedbackTextByID = feedbackService.getFeedbackTextByID(testFeedbackId);
        assertEquals(testFeedback.getFeedback(), feedbackTextByID);
    }

    @Test
    void changeFeedbackByID() {
        String testFeed = "qqq";
        Integer testRating = 3;
        feedbackService.changeFeedbackByID(testFeedbackId, testFeed, testRating);
        List<Feedback> all = feedbackService.getAllByRestaurantID(testRestaurantId);
        Feedback feedback = all.get(0);
        assertEquals(testFeed, feedback.getFeedback());
        assertEquals(testRating, feedback.getRating());
    }

    @AfterAll
    void clear(){
        feedbackService.deleteFeedbackByRestaurantId(testRestaurantId);
        restaurantService.deleteRestaurantByName(testRestaurant.getName());
    }
}
