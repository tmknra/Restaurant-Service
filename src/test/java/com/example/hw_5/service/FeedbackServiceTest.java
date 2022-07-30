package com.example.hw_5.service;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.entity.Feedback;
import com.example.hw_5.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedbackServiceTest extends Hw5ApplicationTests {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private RestaurantService restaurantService;

    private final Restaurant testRestaurant;
    private final Feedback testFeedback;
    private final Integer testRestaurantID = 1;
    private final String testText = "testFeedback";
    private final Integer testRating = 1;

    {
        testFeedback = new Feedback(testRestaurantID, testText, testRating);
        testRestaurant = new Restaurant("test", "test");
    }

    @Test
    void getAllByRestaurantID() {
        List<Feedback> allByRestaurantID = feedbackService.getAllByRestaurantID(testRestaurantID);
        Feedback feedback = allByRestaurantID.get(0);
        assertEquals(testRestaurantID, feedback.getRestaurantID());
    }

    @Test
    void getAverageRatingByRestaurantID() {
        restaurantService.addNewRestaurant(testRestaurant);

        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
        Restaurant restaurant = allRestaurants.get(allRestaurants.size() - 1);
        Integer testID = restaurant.getId();
        Feedback ratingTest = new Feedback(testID, "test", 2);

        feedbackService.addNewFeedback(ratingTest);
        Double averageRating = feedbackService.getAverageRatingByRestaurantID(testID);
        assertEquals(ratingTest.getRating().doubleValue(), averageRating);
    }
    
    @Test
    void addNewFeedback() {
        feedbackService.addNewFeedback(testFeedback);
        List<Feedback> allByRestaurantID = feedbackService.getAllByRestaurantID(1);
        Feedback feedbackFromDB = allByRestaurantID.get(allByRestaurantID.size() - 1);
        assertEquals(testFeedback.getFeedback(), feedbackFromDB.getFeedback());
    }

    @Test
    void getFeedbackTextByID(){
        feedbackService.addNewFeedback(testFeedback);
        List<Feedback> allByRestaurantID = feedbackService.getAllByRestaurantID(testFeedback.getRestaurantID());
        Feedback feedback = allByRestaurantID.get(allByRestaurantID.size()-1);
        assertEquals(testFeedback.getFeedback(), feedback.getFeedback());
    }

    @Test
    void changeFeedbackByID() {
        feedbackService.changeFeedbackByID(testRestaurantID, testText, testRating);
        String feedbackByID = feedbackService.getFeedbackTextByID(testRestaurantID);
        assertEquals(testFeedback.getFeedback(), feedbackByID);
    }
}
