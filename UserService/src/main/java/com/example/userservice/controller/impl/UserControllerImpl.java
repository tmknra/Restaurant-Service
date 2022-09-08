package com.example.userservice.controller.impl;

import com.example.userservice.controller.UserController;
import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import com.example.userservice.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    public UserOutDto createUser(UserInDto user) {
        return userService.createUser(user);
    }

    @SneakyThrows
    @Override
    public UserOutDto updateUser(UserInDto user, long userId) {
        return userService.updateUser(user, userId);
    }

    @SneakyThrows
    @Override
    public void deleteUser(long userId) {
        userService.deleteUser(userId);
    }

    @SneakyThrows
    @Override
    public UserOutDto getUserById(long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public List<UserOutDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
