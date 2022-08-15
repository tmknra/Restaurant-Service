package com.example.hw_5.mapper;

import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.example.hw_5.entity.Restaurant;
import com.google.i18n.phonenumbers.NumberParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

    public abstract RestaurantOutDto restaurantToRestaurantOutDto(Restaurant restaurant);
    @Mapping(target = "id", ignore = true)
    public abstract Restaurant restaurantInDtoToRestaurant(RestaurantInDto restaurant) throws NumberParseException;
}
