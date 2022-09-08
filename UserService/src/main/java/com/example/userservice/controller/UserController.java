package com.example.userservice.controller;

import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserController {
    @PostMapping("/create")
    UserOutDto createUser(@RequestBody UserInDto user);

    @PutMapping("/update/{userId}")
    UserOutDto updateUser(@RequestBody UserInDto user, @PathVariable long userId);

    @DeleteMapping("/delete/{userId}")
    void deleteUser(@PathVariable long userId);

    @GetMapping("/{userId}")
    UserOutDto getUserById(@PathVariable long userId);

    @GetMapping("/all")
    List<UserOutDto> getAllUsers();
}
