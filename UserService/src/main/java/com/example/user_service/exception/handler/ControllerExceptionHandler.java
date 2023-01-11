package com.example.user_service.exception.handler;

import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionHandler  {

    @ExceptionHandler(value
            = {
            UserAlreadyExists.class,
            UserNotFoundException.class,
            InvalidPasswordException.class
    })
    protected ResponseEntity<Object> handleCommonExceptions(
            Exception ex) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", ex.getMessage());
        if (ex.getClass() == UserNotFoundException.class) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(hashMap);
        }
        return ResponseEntity.status(UNAUTHORIZED)
                .body(hashMap);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> response = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,  fieldError -> "Invalid format error!"));

        return ResponseEntity.status(UNAUTHORIZED)
                .body(response);
    }
}
