package com.example.userservice.service;

import com.example.userservice.UserServiceApplicationTests;
import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import com.example.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends UserServiceApplicationTests {
    // TODO: Fix tests with timestamp comparison!
    @Autowired
    private UserService userService;

    @Test
    void createUser() throws UserNotFoundException {
        UserInDto userInDto = UserInDto.builder()
                .lastname("test")
                .name("test")
                .patronymic("test")
                .email("test")
                .build();
        UserOutDto user = userService.createUser(userInDto);
        System.out.println("user = " + userService.getUserById(user.getId()));
        assertEquals(user, userService.getUserById(user.getId()));
    }

    @Test
    void updateUser() throws UserNotFoundException {
        UserInDto userInDto = UserInDto.builder()
                .lastname("test")
                .name("test")
                .patronymic("test")
                .email("test")
                .build();
        UserOutDto user = userService.createUser(userInDto);
        UserOutDto updatedUser = userService.updateUser(userInDto, user.getId());
        assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        UserInDto userInDto = UserInDto.builder()
                .lastname("test")
                .name("test")
                .patronymic("test")
                .email("test")
                .build();
        UserOutDto user = userService.createUser(userInDto);
        userService.deleteUser(user.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void getUserById() throws UserNotFoundException {
        UserInDto userInDto = UserInDto.builder()
                .lastname("test")
                .name("test")
                .patronymic("test")
                .email("test")
                .build();
        UserOutDto user = userService.createUser(userInDto);
        UserOutDto userById = userService.getUserById(user.getId());
        assertEquals(userInDto.getLastname(), userById.getLastname());
        assertEquals(userInDto.getName(), userById.getName());
        assertEquals(userInDto.getPatronymic(), userById.getPatronymic());
        assertEquals(userInDto.getEmail(), userById.getEmail());
        assertEquals(userInDto.getRegistrationDate(), userById.getRegistrationDate());
    }

    @Test
    void getAllUsers() {
        UserInDto userInDto_1 = UserInDto.builder()
                .name("testName1")
                .lastname("testLastname1")
                .patronymic("testPatronymic1")
                .email("testEmail1")
                .build();
        UserInDto userInDto_2 = UserInDto.builder()
                .name("testName2")
                .lastname("testLastname2")
                .patronymic("testPatronymic2")
                .email("testEmail2")
                .build();
        UserInDto userInDto_3 = UserInDto.builder()
                .name("testName3")
                .lastname("testLastname3")
                .patronymic("testPatronymic3")
                .email("testEmail3")
                .build();

        userService.createUser(userInDto_1);
        userService.createUser(userInDto_2);
        userService.createUser(userInDto_3);

        List<UserOutDto> allUsers = userService.getAllUsers();

        assertEquals(userInDto_1.getName(), allUsers.get(0).getName());
        assertEquals(userInDto_2.getName(), allUsers.get(1).getName());
        assertEquals(userInDto_3.getName(), allUsers.get(2).getName());
    }
}