package com.example.user_service.controller.impl;

import com.example.user_service.controller.UserController;
import com.example.user_service.dto.DeleteUserDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private final RabbitTemplate rabbitTemplate;

    public UserControllerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    private UserService userService;

    @Override
    @Operation(summary = "Creates new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New user created successfully."),
            @ApiResponse(
                    responseCode = "401",
                    description = "Provided email already taken.")})
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExists {
        return userService.createUser(user);
    }

    @SneakyThrows
    @Override
    @Operation(summary = "Updates user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated."),
            @ApiResponse(
                    responseCode = "401",
                    description = "Provided email already taken.")})
    public UserOutDto updateUser(UserInDto user, Long userId) {
        return userService.updateUser(user, userId);
    }

    @SneakyThrows
    @Override
    @Operation(summary = "Delete user by id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully deleted."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided user not found.")})
    public void deleteUser(Long oldUserId, Long newUserId) {
        if (getUserById(newUserId) == null) {
            newUserId = null;
        }
        rabbitTemplate.convertAndSend("deleteUserQueue", new DeleteUserDto(oldUserId, newUserId));
        userService.deleteUser(oldUserId);
    }

    @SneakyThrows
    @Override
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns user by id."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided user not found.")})
    public UserOutDto getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    @Operation(summary = "Return all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns users list.")})
    public List<UserOutDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @Operation(summary = "Changing user password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User password successfully changed."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided user not found.")})
    public void changePasswordById(ChangePasswordInDto body) throws UserNotFoundException {
        userService.changePasswordById(body);
    }

}
