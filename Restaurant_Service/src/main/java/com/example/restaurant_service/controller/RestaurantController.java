package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @Operation(summary = "Creates new restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New restaurant successfully created."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Returns when foundation date or phone number is not acceptable")})
    public RestaurantOutDto createRestaurant(@RequestBody RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException {
        return restaurantService.createRestaurant(restaurant);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns restaurant by id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns restaurant by id."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found.")})
    public RestaurantOutDto getRestaurant(@PathVariable Long id) throws RestaurantNotFoundException {
        return restaurantService.getRestaurant(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Returns all restaurants.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns list of restaurants.")})
    public Page<RestaurantOutDto> getAll(Pageable pageable) {
        return restaurantService.getAllRestaurants(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update provided restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated provided restaurant."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found."),
    })
    public void updateDescriptionById(@RequestBody RestaurantInDto restaurantInDto, @PathVariable Long id) throws RestaurantNotFoundException {
        restaurantService.updateRestaurant(restaurantInDto, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted provided restaurant."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided restaurant not found."),
    })
    public void deleteRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        restaurantService.deleteRestaurantById(id);
    }
}
