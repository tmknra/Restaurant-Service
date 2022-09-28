package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.RestaurantMapper;
import com.example.restaurant_service.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.i18n.phonenumbers.NumberParseException;
import com.sun.xml.bind.v2.TODO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    // TODO: put mappers in service
    private final RestaurantMapper restaurantMapper;
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantMapper restaurantMapper,
                                RestaurantService restaurantService) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all")
    @Operation(summary = "Returns all restaurants.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns list of restaurants.")})
    public Page<RestaurantOutDto> getAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.getAllRestaurants(pageable);
        return restaurants.map(restaurantMapper::restaurantToRestaurantOutDto);
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
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurant);
    }

    @PostMapping("/new")
    @Operation(summary = "Creates new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "",
                    description = "")})
    public RestaurantOutDto addNewRestaurant(@RequestBody @Valid RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException {
        Restaurant restaurantEntity = restaurantService.createRestaurant(restaurant);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurantEntity);
    }

    @PostMapping("/delete")
    @Operation(summary = "Creates new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "",
                    description = "")})
    // TODO: refactor to delete by id
    public void deleteRestaurantByName(@RequestBody JsonNode name) {
        restaurantService.deleteRestaurantByName(name.get("name").asText());
    }


    @PutMapping("/description/update/{id}")
    @Operation(summary = "Creates new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "",
                    description = "")})
    // TODO: refactor to update Restaurant
    public void updateDescriptionById(@PathVariable Long id, @RequestBody JsonNode description) throws RestaurantNotFoundException {
        restaurantService.updateDescriptionById(id, description.get("description").asText());
    }

    //
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
    //     Map<String, String> errors = new HashMap<>();
    //     exception.getBindingResult().getAllErrors().forEach(
    //             error -> {
    //                 String fieldName = ((FieldError) error).getField();
    //                 String errorMessage = error.getDefaultMessage();
    //                 errors.put(fieldName, errorMessage);
    //             }
    //     );
    //     return errors;
    // }
}
