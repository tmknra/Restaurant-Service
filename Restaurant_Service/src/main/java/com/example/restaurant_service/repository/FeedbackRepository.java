package com.example.restaurant_service.repository;

import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // @Query(value = "SELECT restaurant_id, feedback, rating FROM feedbacks where restaurant_id = :restaurantId",
    //         nativeQuery = true)
    Page<Feedback> findAllByRestaurantId(Restaurant restaurantId, Pageable pageable);

    @Query("select avg(f.rating) from Feedback f where f.restaurantId = :restaurantId")
    BigDecimal findAverageRatingByRestaurantId(Restaurant restaurantId);
}
