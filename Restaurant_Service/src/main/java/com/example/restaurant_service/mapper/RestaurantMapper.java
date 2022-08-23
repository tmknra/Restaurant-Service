package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.Feedback;
import com.example.restaurant_service.entity.Restaurant;
import com.google.i18n.phonenumbers.NumberParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

    @Autowired
    private FeedbackMapper feedbackMapper;

    public abstract RestaurantOutDto restaurantToRestaurantOutDto(Restaurant restaurant);
    @Mapping(target = "id", ignore = true)
    public abstract Restaurant restaurantInDtoToRestaurant(RestaurantInDto restaurant) throws NumberParseException;

    FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback){
        return feedbackMapper.feedbackToFeedbackOutDto(feedback);
    }
}
