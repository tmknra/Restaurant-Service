package com.example.restaurant_service.controller.impl;

import com.example.restaurant_service.controller.RestaurantController;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.dto.out.RestaurantSmallOutDto;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.OwnerNotFoundException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.FeedbackService;
import com.example.restaurant_service.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantControllerImpl implements RestaurantController {

    private final RestaurantService restaurantService;
    private final FeedbackService feedbackService;

    public RestaurantControllerImpl(RestaurantService restaurantService, FeedbackService feedbackService) {
        this.restaurantService = restaurantService;
        this.feedbackService = feedbackService;
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New restaurant successfully created."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Returns when foundation date or phone number is not acceptable")})
    @Override
    public RestaurantOutDto createRestaurant(RestaurantInDto restaurant) throws NumberParseException,
            FoundationDateIsExpiredException, PhoneNumberNotRuException, OwnerNotFoundException {
        return restaurantService.createRestaurant(restaurant);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns restaurant by id."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found.")})
    @Override
    public RestaurantOutDto getRestaurantById(Long id) throws RestaurantNotFoundException {
        return restaurantService.getRestaurant(id);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns list of restaurants.")})
    @Override
    public Page<RestaurantOutDto> getAllRestaurants(Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    public Page<RestaurantSmallOutDto> getSmallList(Pageable pageable) {
        return restaurantService.getSmallList(pageable);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated provided restaurant."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found."),
    })
    @Override
    public RestaurantOutDto updateRestaurantById(RestaurantInDto restaurantInDto, Long id)
            throws RestaurantNotFoundException, OwnerNotFoundException {
        return restaurantService.updateRestaurant(restaurantInDto, id);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted provided restaurant."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found."),
    })
    @Override
    public ResponseEntity<?> deleteRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        return restaurantService.deleteRestaurantById(id);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns feedback list."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    // TODO: текст отзыва обрезанный до 100 символов, если текст < 100 символов, вернуть полный текст + флаг "полный текст"
    // поля: restaurantId, feedback, flag, rating
    @Override
    public Page<FeedbackOutDto> getFeedbacksByRestaurantId(Pageable pageable, Long id) throws RestaurantNotFoundException {
        return feedbackService.getAllByRestaurantId(pageable, id);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns rating."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant id not found.")})
    @Override
    public ResponseEntity<?> getAverageRatingByRestaurantId(Long id) throws RestaurantNotFoundException {
        return feedbackService.getAverageRatingByRestaurantId(id);
    }

}
