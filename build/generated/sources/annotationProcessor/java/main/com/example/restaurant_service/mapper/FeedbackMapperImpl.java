package com.example.restaurant_service.mapper;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Feedback;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-22T14:41:08+0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class FeedbackMapperImpl extends FeedbackMapper {

    @Override
    public FeedbackOutDto feedbackToFeedbackOutDto(Feedback feedback) {
        if ( feedback == null ) {
            return null;
        }

        Long id = null;
        String feedback1 = null;
        Integer rating = null;

        id = feedback.getId();
        feedback1 = feedback.getFeedback();
        rating = feedback.getRating();

        FeedbackOutDto feedbackOutDto = new FeedbackOutDto( id, feedback1, rating );

        return feedbackOutDto;
    }

    @Override
    public Feedback feedbackInDtoToFeedback(FeedbackInDto feedback) {
        if ( feedback == null ) {
            return null;
        }

        Feedback feedback1 = new Feedback();

        feedback1.setId( feedback.getId() );
        feedback1.setFeedback( feedback.getFeedback() );
        feedback1.setRating( feedback.getRating() );

        return feedback1;
    }
}
