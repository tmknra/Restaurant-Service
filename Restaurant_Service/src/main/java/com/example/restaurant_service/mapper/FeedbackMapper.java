package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    @Mapping(target = "restaurantId", source = "restaurantId.id")
    public abstract FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantId.id", source = "restaurantId")
    public abstract Feedback feedbackInDtoToFeedback(FeedbackInDto feedback);
}
