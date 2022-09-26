package com.example.restaurant_service.controller;

import com.example.restaurant_service.RabbitTest;
import com.example.restaurant_service.dto.DeleteUserDto;
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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantMapper restaurantMapper,
                                RestaurantService restaurantService) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all")
    public Page<RestaurantOutDto> getAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.getAllRestaurants(pageable);
        return restaurants.map(restaurantMapper::restaurantToRestaurantOutDto);
    }

    @GetMapping("/{id}")
    public RestaurantOutDto getRestaurant(@PathVariable Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurant);
    }

    @GetMapping("/{id}/founded")
    public LocalDate getFoundationDate(@PathVariable Long id) throws RestaurantNotFoundException {
        return restaurantService.getFoundationDateById(id);
    }

    @PostMapping("/new")
    public RestaurantOutDto addNewRestaurant(@RequestBody @Valid RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException {
        Restaurant restaurantEntity = restaurantService.createRestaurant(restaurant);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurantEntity);
    }

    @PostMapping("/delete")
    public void deleteRestaurantByName(@RequestBody JsonNode name) {
        restaurantService.deleteRestaurantByName(name.get("name").asText());
    }

    @PostMapping("/set_email/{id}")
    public void setEmail(@PathVariable Long id, @RequestBody JsonNode email_address) throws JsonProcessingException, RestaurantNotFoundException {
        restaurantService.setEmailById(id, email_address.get("email_address").asText());
    }

    @PostMapping("/set_phone/{id}")
    public void setPhoneNumber(@PathVariable Long id, @RequestBody JsonNode phone_number) throws NumberParseException, RestaurantNotFoundException, PhoneNumberNotRuException {
        restaurantService.setPhoneNumberById(id, phone_number.get("phone_number").asText());
    }

    @PostMapping("/set_foundation_date/{id}")
    public void setFoundationDate(@PathVariable Long id, @RequestBody JsonNode foundationDate) throws FoundationDateIsExpiredException, RestaurantNotFoundException {
        LocalDate foundationDateFromJson = LocalDate.parse(foundationDate.get("foundation_date").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        restaurantService.setFoundationDateById(id, foundationDateFromJson);
    }

    @PutMapping("/description/update/{id}")
    public void updateDescriptionById(@PathVariable Long id, @RequestBody JsonNode description) throws RestaurantNotFoundException {
        restaurantService.updateDescriptionById(id, description.get("description").asText());
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return errors;
    }
}
