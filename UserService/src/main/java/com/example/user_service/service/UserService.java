package com.example.user_service.service;

import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserOutDto createUser(UserInDto user) throws UserAlreadyExistsException;
    UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException, UserAlreadyExistsException;
    ResponseEntity<?> deleteUser(Long userId) throws UserNotFoundException;

    UserOutDto getUserById(Long id) throws UserNotFoundException;
    List<UserOutDto> getAllUsers(Pageable pageable);

    ResponseEntity<?> changePasswordById(ChangePasswordInDto changePasswordInDto) throws UserNotFoundException, InvalidPasswordException;

    ResponseEntity<?> updateUserToRestaurant(UpdateRestaurantOwnerDto updateRestaurantOwnerDto) throws UserNotFoundException;
}
