package com.example.restaurant_service.repository;

import com.example.restaurant_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);
    List<Restaurant> findAllByOwnerId(Long ownerId);
}
