package com.example.hw_5.repository;

import com.example.hw_5.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByRestaurantid(Long restaurantId);

    @Query("select avg(rating) from Feedback where restaurantid = ?1")
    Double findAverageRatingByRestaurantid(@Param("restaurant_id") Long restaurant_id);
}
