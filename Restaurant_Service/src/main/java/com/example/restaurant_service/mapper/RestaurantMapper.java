package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.dto.out.RestaurantSmallOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.repository.data.RestaurantSmall;
import com.google.i18n.phonenumbers.NumberParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {


    public abstract RestaurantOutDto restaurantToRestaurantOutDto(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "updateDatetime", ignore = true)
    public abstract Restaurant restaurantInDtoToRestaurant(RestaurantInDto restaurant) throws NumberParseException;

    public abstract RestaurantSmallOutDto restaurantSmallToRestaurantSmallOutDto(RestaurantSmall restaurantSmall);

}
