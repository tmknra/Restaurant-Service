package com.example.user_service.service;

import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserOutDto createUser(UserInDto user) throws UserAlreadyExists;
    UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException, UserAlreadyExists;
    ResponseEntity<?> deleteUser(Long userId) throws UserNotFoundException;

    UserOutDto getUserById(Long id) throws UserNotFoundException;
    List<UserOutDto> getAllUsers();

    ResponseEntity<?> changePasswordById(ChangePasswordInDto changePasswordInDto) throws UserNotFoundException, InvalidPasswordException;

    ResponseEntity<?> updateUserToRestaurant(UpdateRestaurantOwnerDto updateRestaurantOwnerDto) throws UserNotFoundException;
}
