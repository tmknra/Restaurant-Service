package com.example.hw_5.exception.handler;

import com.example.hw_5.exception.entity.PhoneNumberNotRuException;
import com.example.hw_5.exception.entity.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {RestaurantNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(
            RestaurantNotFoundException ex, WebRequest request) {

        String bodyOfResponse = ex.toString();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = {NumberParseException.class})
    protected ResponseEntity<Object> handleConflict(
            NumberParseException ex, WebRequest request) {

        String bodyOfResponse = ex.toString();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {PhoneNumberNotRuException.class})
    protected ResponseEntity<Object> handleConflict(
            PhoneNumberNotRuException ex, WebRequest request) {

        String bodyOfResponse = ex.toString();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
