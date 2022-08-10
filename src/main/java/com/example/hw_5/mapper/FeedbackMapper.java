package com.example.hw_5.mapper;

import com.example.hw_5.dto.in.FeedbackInDto;
import com.example.hw_5.dto.out.FeedbackOutDto;
import com.example.hw_5.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    public abstract FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback);

    public abstract Feedback feedbackInDtoToFeedback(FeedbackInDto feedback);
}
