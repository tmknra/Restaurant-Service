package com.example.restaurant_service.exception.handler;

import com.example.restaurant_service.exception.entity.FeedbackNotFoundException;
import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.example.restaurant_service.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestaurantControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {
            NumberParseException.class,
            PhoneNumberNotRuException.class,
            RestaurantNotFoundException.class,
            FoundationDateIsExpiredException.class,
            FeedbackNotFoundException.class
    })
    protected ResponseEntity<Object> handleConflict(
            Exception ex) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", ex.getMessage());
        if (ex.getClass() == RestaurantNotFoundException.class || ex.getClass() == FeedbackNotFoundException.class) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(hashMap);
        }
        return ResponseEntity.status(BAD_REQUEST)
                .body(hashMap);
    }
}
