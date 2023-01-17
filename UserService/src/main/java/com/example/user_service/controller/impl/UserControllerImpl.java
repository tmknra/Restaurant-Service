package com.example.user_service.controller.impl;

import com.example.user_service.controller.UserController;
import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New user created successfully."),
            @ApiResponse(
                    responseCode = "401",
                    description = "Provided email already taken.")})
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExistsException {
        return userService.createUser(user);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated."),
            @ApiResponse(
                    responseCode = "401",
                    description = "Provided email already taken.")})
    public UserOutDto updateUser(UserInDto user, Long userId) throws UserNotFoundException, UserAlreadyExistsException {
        return userService.updateUser(user, userId);
    }

    @SneakyThrows
    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully deleted."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided user not found.")})
    public ResponseEntity<?> deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @SneakyThrows
    @Override
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns users list.")})
    public List<UserOutDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User password successfully changed."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Provided user not found.")})
    public ResponseEntity<?> changePasswordById(ChangePasswordInDto body)
            throws UserNotFoundException, InvalidPasswordException {
        return userService.changePasswordById(body);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Owner successfully updated."),
            @ApiResponse(
                    responseCode = "404",
                    description = "One of the provided users not found.")})
    public ResponseEntity<?> updateRestaurantOwner(UpdateRestaurantOwnerDto updateRestaurantOwnerDto) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUserToRestaurant(updateRestaurantOwnerDto));
    }

}
