package com.example.userservice.service;

import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import com.example.userservice.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserOutDto createUser(UserInDto user);
    UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException;
    void deleteUser(long userId) throws UserNotFoundException;

    UserOutDto getUserById(long id) throws UserNotFoundException;
    List<UserOutDto> getAllUsers();
}
