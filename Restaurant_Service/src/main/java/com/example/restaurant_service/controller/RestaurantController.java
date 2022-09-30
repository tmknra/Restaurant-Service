package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.dto.out.RestaurantSmallOutDto;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.OwnerNotFoundException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurants")
public interface RestaurantController {

    @PostMapping
    @Operation(summary = "Creates new restaurant")
    ResponseEntity<?> createRestaurant(@RequestBody RestaurantInDto restaurant) throws NumberParseException,
            FoundationDateIsExpiredException, PhoneNumberNotRuException, OwnerNotFoundException;

    @GetMapping("/{id}")
    @Operation(summary = "Returns restaurant by id.")
    ResponseEntity<?> getRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException;

    @GetMapping("/all")
    @Operation(summary = "Returns all restaurants.")
    Page<RestaurantOutDto> getAllRestaurants(Pageable pageable);

    @GetMapping("/smallList")
    Page<RestaurantSmallOutDto> getSmallList(Pageable pageable);

    @PutMapping("/{id}")
    @Operation(summary = "Update provided restaurant")
    ResponseEntity<?> updateRestaurantById(@RequestBody RestaurantInDto restaurantInDto, @PathVariable Long id)
            throws RestaurantNotFoundException, OwnerNotFoundException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant by id")
    ResponseEntity<?> deleteRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException;

    @GetMapping("/{id}/feedbacks")
    @Operation(summary = "Returns feedback list by restaurant id")
    Page<FeedbackOutDto> getFeedbacksByRestaurantId(Pageable pageable, @PathVariable Long id) throws RestaurantNotFoundException;

    @GetMapping("/{id}/rating")
    @Operation(summary = "Returns average restaurant rating.")
    ResponseEntity<?> getAverageRatingByRestaurantId(@PathVariable Long id) throws RestaurantNotFoundException;
}
