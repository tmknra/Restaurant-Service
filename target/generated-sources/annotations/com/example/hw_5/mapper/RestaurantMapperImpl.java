package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.google.i18n.phonenumbers.NumberParseException;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T13:26:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class RestaurantMapperImpl extends RestaurantMapper {

    @Override
    public RestaurantOutDto restaurantToRestaurantOutDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantOutDto.RestaurantOutDtoBuilder restaurantOutDto = RestaurantOutDto.builder();

        restaurantOutDto.id( restaurant.getId() );
        restaurantOutDto.name( restaurant.getName() );
        restaurantOutDto.description( restaurant.getDescription() );
        restaurantOutDto.phone_number( restaurant.getPhone_number() );
        restaurantOutDto.email_address( restaurant.getEmail_address() );
        restaurantOutDto.foundation_date( restaurant.getFoundation_date() );

        return restaurantOutDto.build();
    }

    @Override
    public Restaurant restaurantInDtoToRestaurant(RestaurantInDto restaurant) throws NumberParseException {
        if ( restaurant == null ) {
            return null;
        }

        Restaurant.RestaurantBuilder restaurant1 = Restaurant.builder();

        restaurant1.name( restaurant.getName() );
        restaurant1.description( restaurant.getDescription() );
        restaurant1.phone_number( restaurant.getPhone_number() );
        restaurant1.email_address( restaurant.getEmail_address() );
        restaurant1.foundation_date( restaurant.getFoundation_date() );

        return restaurant1.build();
    }
}
