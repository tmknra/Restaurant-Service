package com.example.restaurant_service.service.impl;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
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
import java.util.List;
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
    public RestaurantOutDto createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException {
        Restaurant restaurantEntity = restaurantMapper.restaurantInDtoToRestaurant(restaurant);
        if (restaurantEntity.getPhone_number() != null && restaurantEntity.getPhone_number().length() != 0)
            restaurantEntity.setPhone_number(Util.reformatRuTelephone(restaurant.getPhone_number()));
        if (restaurant.getFoundation_date() != null)
            Util.validateFoundationDate(restaurant.getName(), restaurant.getFoundation_date());

        return restaurantMapper.restaurantToRestaurantOutDto(restaurantRepository.save(restaurantEntity));
    }

    @Override
    public RestaurantOutDto getRestaurant(Long id) throws RestaurantNotFoundException {
        return restaurantMapper.restaurantToRestaurantOutDto(getRestaurantById(id));
    }

    @Override
    public Page<RestaurantOutDto> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(restaurantMapper::restaurantToRestaurantOutDto);
    }

    @Override
    public List<Restaurant> getRestaurantsByOwnerId(Long id) {
        return restaurantRepository.findAllByOwnerId(id);
    }

    @Override
    @Transactional
    public void updateRestaurant(RestaurantInDto restaurantInDto, Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = getRestaurantById(id);
        if (restaurantInDto.getName() != null)
            restaurant.setName(restaurantInDto.getName());

        if (restaurantInDto.getDescription() != null)
            restaurant.setDescription(restaurantInDto.getDescription());

        if (restaurantInDto.getFoundation_date() != null)
            restaurant.setFoundation_date(restaurantInDto.getFoundation_date());

        if (restaurantInDto.getPhone_number() != null)
            restaurant.setPhone_number(restaurantInDto.getPhone_number());

        if (restaurantInDto.getEmail_address() != null)
            restaurant.setEmail_address(restaurantInDto.getEmail_address());

        if (restaurantInDto.getOwnerId() != null)
            restaurant.setOwnerId(restaurantInDto.getOwnerId());
    }

    @Transactional
    public void deleteRestaurantById(Long id) throws RestaurantNotFoundException {
        restaurantRepository.delete(getRestaurantById(id));
    }

    private Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty())
            throw new RestaurantNotFoundException();
        return byId.get();
    }
}
