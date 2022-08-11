package com.example.hw_5.controller;

import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import com.example.hw_5.mapper.RestaurantMapper;
import com.example.hw_5.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public List<RestaurantOutDto> getAll() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantOutDto getRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurant);
    }

    @GetMapping("/{id}/founded")
    public LocalDate getFoundationDate(@PathVariable Long id) {
        return restaurantService.getFoundationDateById(id);
    }

    @PostMapping("/new")
    public RestaurantOutDto addNewRestaurant(@RequestBody RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException {
        System.out.println(restaurant);
        Restaurant restaurantEntity = restaurantService.createRestaurant(restaurant);
        return restaurantMapper.restaurantToRestaurantOutDto(restaurantEntity);
    }

    @PostMapping("/delete")
    public void deleteRestaurantByName(@RequestBody JsonNode name) {
        restaurantService.deleteRestaurantByName(name.get("name").asText());
    }

    @PostMapping("/set_email/{id}")
    public void setEmail(@PathVariable Long id, @RequestBody JsonNode email_address) throws JsonProcessingException {
        restaurantService.setEmailById(id, email_address.get("email_address").asText());
    }

    @PostMapping("/set_phone/{id}")
    public void setPhoneNumber(@PathVariable Long id, @RequestBody JsonNode phone_number) throws NumberParseException {
        restaurantService.setPhoneNumberById(id, phone_number.get("phone_number").asText());
    }

    @PostMapping("/set_foundation_date/{id}")
    public void setFoundationDate(@PathVariable Long id, @RequestBody JsonNode foundationDate) throws FoundationDateIsExpiredException {
        LocalDate foundationDateFromJson = LocalDate.parse(foundationDate.get("foundation_date").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        restaurantService.setFoundationDateById(id, foundationDateFromJson);
    }
}
