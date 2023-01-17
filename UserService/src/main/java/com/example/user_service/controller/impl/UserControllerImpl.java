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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserControllerImpl implements UserController {


    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        log.info("Initialized UserController");
        this.userService = userService;
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New user created successfully."),
            @ApiResponse(
                    responseCode = "401",
                    description = "Provided email already taken.")})
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExistsException {
        log.info("Got POST request from client to create new user with 'email:{}'", user.getEmail());
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
    public UserOutDto updateUser(UserInDto user, Long userId)
            throws UserNotFoundException, UserAlreadyExistsException, InvalidPasswordException {
        log.info("Got PUT request from client to update user");
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
        log.info("Got DELETE request from client to delete user");
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
        log.info("Got GET request from client to get user");
        return userService.getUserById(userId);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns users list.")})
    public List<UserOutDto> getAllUsers(Pageable pageable) {
        log.info("Got GET request from client to get list of users");
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
        log.info("Got PUT request from client to change user password");
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
        log.info("Got PUT request from client to update restaurants owner");
        return ResponseEntity.ok(userService.updateUserToRestaurant(updateRestaurantOwnerDto));
    }

}
