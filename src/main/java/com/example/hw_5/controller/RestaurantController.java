package com.example.hw_5.controller;

import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.FoundationDateIsExpiredException;
import com.example.hw_5.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Resource
    private RestaurantService restaurantService;

    @GetMapping("/all")
    public List<Restaurant> getAll() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{name}")
    public String getDescription(@PathVariable String name) {
        return restaurantService.getDescriptionByName(name);
    }

    @GetMapping("/{id}/founded")
    public String getFoundationDate(@PathVariable Long id) {
        return restaurantService.getFoundationDateById(id);
    }

    @PutMapping("/new")
    public void addNewRestaurant(@RequestBody Restaurant restaurant) throws FoundationDateIsExpiredException {
        restaurantService.addNewRestaurant(restaurant);
    }

    @PutMapping("/delete")
    public void deleteRestaurantByName(@RequestBody String name) {
        restaurantService.deleteRestaurantByName(name);
    }

    @PutMapping("/set_email/{id}")
    public void setEmail(@PathVariable Long id, @RequestBody String email_address) {
        restaurantService.setEmailById(id, email_address);
    }

    @PutMapping("/set_phone/{id}")
    public void setPhoneNumber(@PathVariable Long id, @RequestBody String phone_number) throws NumberParseException {
        restaurantService.setPhoneNumberById(id, phone_number);
    }

    @PutMapping("/set_foundation_date/{id}")
    public void setFoundationDate(@PathVariable Long id, @RequestBody String foundationDate) throws FoundationDateIsExpiredException {
        restaurantService.setFoundationDateById(id, foundationDate);
    }

    @PutMapping("/change_description")
    public void changeDescription(@RequestBody Restaurant restaurant) {
        restaurantService.changeDescriptionByName(restaurant.getName(), restaurant.getDescription());
    }
}
