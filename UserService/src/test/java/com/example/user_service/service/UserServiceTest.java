package com.example.user_service.service;

import com.example.user_service.UserServiceApplicationTests;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends UserServiceApplicationTests {
    @Autowired
    private UserService userService;
    private Long testUserId;
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @BeforeAll
    void setUp() throws UserAlreadyExists {
        UserInDto userInDto = UserInDto.builder()
                .lastname("test")
                .name("testName1")
                .patronymic("test")
                .email("test")
                .password("testPassword")
                .build();
        UserOutDto user = userService.createUser(userInDto);
        this.testUserId = user.getId();

        UserInDto userInDto_2 = UserInDto.builder()
                .name("testName2")
                .lastname("testLastname2")
                .patronymic("testPatronymic2")
                .email("testEmail2")
                .password("testPassword2")
                .build();
        UserInDto userInDto_3 = UserInDto.builder()
                .name("testName3")
                .lastname("testLastname3")
                .patronymic("testPatronymic3")
                .email("testEmail3")
                .password("testPassword3")
                .build();

        userService.createUser(userInDto_2);
        userService.createUser(userInDto_3);
    }

    @Test
    void updateUser() throws UserNotFoundException, UserAlreadyExists {
        UserInDto userInDto = UserInDto.builder()
                .lastname("updatedLastname")
                .name("updatedName")
                .patronymic("asd")
                .email("updatedMail")
                .build();

        userService.updateUser(userInDto, testUserId);
        UserOutDto userById = userService.getUserById(testUserId);

        assertEquals("updatedLastname", userById.getLastname());
        assertEquals("updatedName", userById.getName());
        assertEquals("updatedMail", userById.getEmail());
    }


    @Test
    void getUserById() throws UserNotFoundException, UserAlreadyExists {
        UserInDto userInDto = UserInDto.builder()
                .lastname("getTest")
                .name("getTest")
                .patronymic("test")
                .email("getTest")
                .password("testPassword")
                .build();
        UserOutDto user = userService.createUser(userInDto);

        UserOutDto userById = userService.getUserById(user.getId());
        assertEquals("getTest", userById.getLastname());
        assertEquals("getTest", userById.getName());
        assertEquals("getTest", userById.getEmail());
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        userService.deleteUser(testUserId);
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(testUserId));
    }
    // @Test
    // void getAllUsers() {
    //     List<UserOutDto> allUsers = userService.getAllUsers();
    //
    //     assertEquals("testName1", allUsers.get(0).getName());
    //     assertEquals("testName2", allUsers.get(1).getName());
    //     assertEquals("testName3", allUsers.get(2).getName());
    // }

}