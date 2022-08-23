package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T13:26:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class FeedbackMapperImpl extends FeedbackMapper {

    @Override
    public FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        Long id = null;
        Long restaurantid = null;
        String feedback1 = null;
        Integer rating = null;

        id = feedback.getId();
        restaurantid = feedback.getRestaurantid();
        feedback1 = feedback.getFeedback();
        rating = feedback.getRating();

        FeedbackOutDto feedbackOutDto = new FeedbackOutDto( id, restaurantid, feedback1, rating );

        return feedbackOutDto;
    }

    @Override
    public Feedback feedbackInDtoToFeedback(FeedbackInDto feedback) {
        if ( feedback == null ) {
            return null;
        }

        Feedback.FeedbackBuilder feedback1 = Feedback.builder();

        feedback1.id( feedback.getId() );
        feedback1.restaurantid( feedback.getRestaurantid() );
        feedback1.feedback( feedback.getFeedback() );
        feedback1.rating( feedback.getRating() );

        return feedback1.build();
    }
}
