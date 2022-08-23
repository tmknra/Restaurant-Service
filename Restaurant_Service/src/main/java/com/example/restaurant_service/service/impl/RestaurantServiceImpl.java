package com.example.restaurant_service.service.impl;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.mapper.RestaurantMapper;
import com.example.restaurant_service.repository.RestaurantRepository;
import com.example.restaurant_service.service.RestaurantService;
import com.example.restaurant_service.util.Util;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public long createRestaurantByName(String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        Restaurant save = restaurantRepository.save(restaurant);
        return save.getId();
    }

    @Override
    public long createRestaurantByNameAndDate(String name, LocalDate foundationDate) throws FoundationDateIsExpiredException {
        if (foundationDate == null || LocalDate.now().isBefore(foundationDate)) {
            throw new FoundationDateIsExpiredException(name, foundationDate);
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setFoundation_date(foundationDate);
        Restaurant save = restaurantRepository.save(restaurant);
        return save.getId();
    }

    @Override
    public long createRestaurantByNameAndPhoneNumber(String name, String phoneNumber) throws NumberParseException, PhoneNumberNotRuException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        String reformatRuTelephone = Util.reformatRuTelephone(phoneNumber);
        restaurant.setPhone_number(reformatRuTelephone);
        Restaurant save = restaurantRepository.save(restaurant);
        return save.getId();
    }

    @Override
    public Restaurant getRestaurant(Long id) throws RestaurantNotFoundException {
        return getRestaurantById(id);
    }

    @Override
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
        // List<Restaurant> all = restaurantRepository.findAll();
        // List<RestaurantOutDto> outDtos = new ArrayList<>();
        // for (Restaurant restaurant : all) {
        //     outDtos.add(
        //             RestaurantOutDto.builder()
        //                     .id(restaurant.getId())
        //                     .name(restaurant.getName())
        //                     .description(restaurant.getDescription())
        //                     .phone_number(restaurant.getPhone_number())
        //                     .email_address(restaurant.getEmail_address())
        //                     .foundation_date(restaurant.getFoundation_date())
        //                     .build()
        //     );
        // }
        // return outDtos;
    }

    @Override
    public LocalDate getFoundationDateById(Long id) throws RestaurantNotFoundException {
        Restaurant restaurantById = getRestaurantById(id);
        return restaurantById.getFoundation_date();
    }

    @Override
    public Restaurant createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException {
        Restaurant restaurantEntity = restaurantMapper.restaurantInDtoToRestaurant(restaurant);
        if (restaurantEntity.getPhone_number() != null && restaurantEntity.getPhone_number().length() != 0)
            restaurantEntity.setPhone_number(Util.reformatRuTelephone(restaurant.getPhone_number()));
        if (restaurant.getFoundation_date() != null)
            Util.validateFoundationDate(restaurant.getName(), restaurant.getFoundation_date());
        return restaurantRepository.save(restaurantEntity);
    }

    @Override
    @Transactional
    public void updateDescriptionById(Long id, String newDescription) throws RestaurantNotFoundException {
        getRestaurant(id).setDescription(newDescription);
    }

    @Override
    public void setEmailById(Long id, String email) throws RestaurantNotFoundException {
        Restaurant restaurant = getRestaurant(id);
        if (Util.validateEmailAddress(email))
            restaurant.setEmail_address(email);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void setPhoneNumberById(Long id, String number) throws NumberParseException, RestaurantNotFoundException, PhoneNumberNotRuException {
        Restaurant restaurant = getRestaurant(id);
        String reformattedTelephone = Util.reformatRuTelephone(number);
        restaurant.setPhone_number(reformattedTelephone);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void setFoundationDateById(Long id, LocalDate date) throws FoundationDateIsExpiredException, RestaurantNotFoundException {
        Restaurant restaurant = getRestaurant(id);
        Util.validateFoundationDate(restaurant.getName(), date);
        restaurant.setFoundation_date(date);
        restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurantByName(String name) {
        Restaurant restaurantByName = restaurantRepository.findByName(name);
        restaurantRepository.delete(restaurantByName);
    }


    private Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty())
            throw new RestaurantNotFoundException();
        return byId.get();
    }
}
