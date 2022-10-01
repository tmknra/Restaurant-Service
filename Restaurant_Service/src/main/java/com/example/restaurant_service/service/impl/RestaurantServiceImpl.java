package com.example.restaurant_service.service.impl;

import com.example.restaurant_service.dto.DeleteRestaurantOwnerDto;
import com.example.restaurant_service.dto.UpdateRestaurantOwnerDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.dto.out.RestaurantSmallOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.OwnerNotFoundException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.example.restaurant_service.feign.UserServiceClient;
import com.example.restaurant_service.mapper.RestaurantMapper;
import com.example.restaurant_service.repository.RestaurantRepository;
import com.example.restaurant_service.service.RestaurantService;
import com.example.restaurant_service.util.Util;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserServiceClient userServiceClient;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 RestaurantMapper restaurantMapper, UserServiceClient userServiceClient) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public RestaurantOutDto createRestaurant(RestaurantInDto restaurant) throws NumberParseException, FoundationDateIsExpiredException, PhoneNumberNotRuException, OwnerNotFoundException {
        if (restaurant.getOwnerId() != null && userServiceClient.getUser(restaurant.getOwnerId()).getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new OwnerNotFoundException(String.format("Owner %d not found!", restaurant.getOwnerId()));
        }
        Restaurant restaurantEntity = restaurantMapper.restaurantInDtoToRestaurant(restaurant);
        restaurantEntity.setIsDeleted(false);
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
    public RestaurantOutDto updateRestaurant(RestaurantInDto restaurantInDto, Long id) throws RestaurantNotFoundException, OwnerNotFoundException {
        if (userServiceClient.getUser(restaurantInDto.getOwnerId()).getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new OwnerNotFoundException(String.format("Owner id{%d} not found!", restaurantInDto.getOwnerId()));
        }
        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(restaurantInDto.getName());
        restaurant.setDescription(restaurantInDto.getDescription());
        restaurant.setFoundation_date(restaurantInDto.getFoundation_date());
        restaurant.setPhone_number(restaurantInDto.getPhone_number());
        restaurant.setEmail_address(restaurantInDto.getEmail_address());
        restaurant.setOwnerId(restaurantInDto.getOwnerId());
        restaurant.setUpdateDatetime(LocalDate.now());

        return getRestaurant(id);
    }

    @Override
    public Page<RestaurantSmallOutDto> getSmallList(Pageable pageable) {
        return restaurantRepository.findSmallRestaurants(pageable)
                .map(restaurantMapper::restaurantSmallToRestaurantSmallOutDto);
    }

    @Override
    @Transactional
    public void updateOwnerForRestaurants(UpdateRestaurantOwnerDto updateRestaurantOwnerDto) {
        restaurantRepository.updateOwner(updateRestaurantOwnerDto.getOldUserId(), updateRestaurantOwnerDto.getNewUserId());
    }

    @Override
    @Transactional
    public void deleteOwnerFromRestaurants(DeleteRestaurantOwnerDto deleteRestaurantOwnerDto) {
        restaurantRepository.deleteOwnerFromRestaurants(deleteRestaurantOwnerDto.getOwnerId());
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteRestaurantById(Long id) throws RestaurantNotFoundException {
        Restaurant restaurantById = getRestaurantById(id);
        restaurantRepository.delete(restaurantById);
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "Restaurant successfully deleted.");
        return ResponseEntity.ok(message);
    }

    private Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty())
            throw new RestaurantNotFoundException("Restaurant not found!");
        return byId.get();
    }
}
