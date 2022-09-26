package com.example.user_service.controller;

import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
public interface UserController {

    @PostMapping("/create")
    UserOutDto createUser(@RequestBody UserInDto user) throws UserAlreadyExists;

    @PutMapping("/update/{userId}")
    UserOutDto updateUser(@RequestBody UserInDto user, @PathVariable Long userId);

    @DeleteMapping("/delete/{oldUserId}/{newUserId}")
    void deleteUser(@PathVariable Long oldUserId,@PathVariable Long newUserId);

    @GetMapping("/{userId}")
    UserOutDto getUserById(@PathVariable Long userId);

    @GetMapping("/all")
    List<UserOutDto> getAllUsers();

    @PutMapping("/change_pass")
    void changePasswordById(@RequestBody @Valid ChangePasswordInDto body) throws UserNotFoundException;

}
