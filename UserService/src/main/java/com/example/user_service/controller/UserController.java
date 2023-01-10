package com.example.user_service.controller;

import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
public interface UserController {

    @Operation(summary = "Creates new user")
    @PostMapping
    UserOutDto createUser(@RequestBody @Valid UserInDto user) throws UserAlreadyExists;

    @Operation(summary = "Updates user by id")
    @PutMapping("/{userId}")
    UserOutDto updateUser(@RequestBody @Valid UserInDto user, @PathVariable Long userId) throws UserNotFoundException, UserAlreadyExists;

    @Operation(summary = "Delete user by id.")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id);

    @Operation(summary = "Get user by id")
    @GetMapping("/{userId}")
    UserOutDto getUserById(@PathVariable Long userId);

    @Operation(summary = "Return all users")
    @GetMapping("/all")
    List<UserOutDto> getAllUsers();

    @Operation(summary = "Changing user password")
    @PutMapping("/pass")
    ResponseEntity<?> changePasswordById(@RequestBody @Valid ChangePasswordInDto body) throws UserNotFoundException, InvalidPasswordException;

    @Operation(summary = "Updates restaurant owner")
    @PutMapping("/updateOwner")
    ResponseEntity<?> updateRestaurantOwner(@RequestBody UpdateRestaurantOwnerDto updateRestaurantOwnerDto) throws UserNotFoundException;
}
