package com.example.restaurant_service.repository;

import com.example.restaurant_service.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByRestaurantId(Long restaurantId);
    Page<Feedback> findAllByRestaurantId(Pageable pageable, Long restaurantId);

    @Query("select avg(f.rating) from Feedback f where f.restaurant.id = :restaurantid")
    Double findAverageRatingByRestaurantid(Long restaurantid);
}
