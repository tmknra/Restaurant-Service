package com.example.user_service.service;

import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserOutDto createUser(UserInDto user) throws UserAlreadyExists;
    UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException, UserAlreadyExists;
    void deleteUser(long userId) throws UserNotFoundException;

    UserOutDto getUserById(long id) throws UserNotFoundException;
    List<UserOutDto> getAllUsers();

    void changePasswordById(ChangePasswordInDto changePasswordInDto) throws UserNotFoundException;
}
