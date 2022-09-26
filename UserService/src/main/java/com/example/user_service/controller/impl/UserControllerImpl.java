package com.example.user_service.controller.impl;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.example.user_service.controller.UserController;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.DeleteUserDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.service.UserService;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {

    private final RabbitTemplate rabbitTemplate;

    public UserControllerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    private UserService userService;

    @Override
    public String test() {
        // rabbitTemplate.convertAndSend("myQueue", "Hello World!");
        return "test";
    }

    @Override
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExists {
        // System.out.println(user);
        return userService.createUser(user);
    }

    @SneakyThrows
    @Override
    public UserOutDto updateUser(UserInDto user, Long userId) {
        return userService.updateUser(user, userId);
    }

    @SneakyThrows
    @Override
    public void deleteUser(Long oldUserId, Long newUserId) {
        if (getUserById(newUserId) == null) {
            newUserId = null;
        }
            rabbitTemplate.convertAndSend("deleteUserQueue", new DeleteUserDto(oldUserId, newUserId));
            userService.deleteUser(oldUserId);
    }

    @SneakyThrows
    @Override
    public UserOutDto getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public List<UserOutDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public void changePasswordById(ChangePasswordInDto body) throws UserNotFoundException {
        userService.changePasswordById(body);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return errors;
    }
}
