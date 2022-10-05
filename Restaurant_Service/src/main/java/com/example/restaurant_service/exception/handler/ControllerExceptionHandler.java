package com.example.restaurant_service.exception.handler;

import com.example.restaurant_service.exception.entity.*;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {
            NumberParseException.class,
            PhoneNumberNotRuException.class,
            RestaurantNotFoundException.class,
            FoundationDateIsExpiredException.class,
            FeedbackNotFoundException.class,
            MethodArgumentNotValidException.class,
            OwnerNotFoundException.class
    })
    protected ResponseEntity<Object> handleConflict(
            Exception ex) {
        HashMap<String, String> message = new HashMap<>();
        message.put("message", ex.getMessage());
        if (ex.getClass() == RestaurantNotFoundException.class ||
                ex.getClass() == FeedbackNotFoundException.class ||
                ex.getClass() == OwnerNotFoundException.class)
        {
            return ResponseEntity.status(NOT_FOUND)
                    .body(message);
        }
        return ResponseEntity.status(BAD_REQUEST)
                .body(message);
    }
}
