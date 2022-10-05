package com.example.user_service.exception.handler;

import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {UserAlreadyExists.class, UserNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(
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
}
