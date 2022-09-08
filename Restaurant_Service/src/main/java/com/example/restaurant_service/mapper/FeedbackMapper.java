package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    public abstract FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback);

    public abstract Feedback feedbackInDtoToFeedback(FeedbackInDto feedback);
}
