package com.example.user_service.controller;

import com.example.user_service.UserServiceApplicationTests;
import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest extends UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    private Long testUserId;

    @BeforeAll
    void setUp() throws Exception {
        UserInDto user = UserInDto.builder()
                .name("testName")
                .lastname("lastname")
                .patronymic("patronymic")
                .email("testMail@mail.qq")
                .password("1234Qwe#")
                .build();
        String json = objectMapper.writeValueAsString(user);
        String contentAsString = this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        testUserId = objectMapper.readValue(contentAsString, UserOutDto.class).getId();

        UserInDto user1 = UserInDto.builder()
                .name("testName1")
                .lastname("lastname")
                .patronymic("patronymic")
                .email("testMail1@mail.mm")
                .build();
        UserInDto user2 = UserInDto.builder()
                .name("testName2")
                .lastname("lastname")
                .patronymic("patronymic")
                .email("testMail2@mai.mm")
                .build();

        String json1 = objectMapper.writeValueAsString(user1);
        String json2 = objectMapper.writeValueAsString(user2);

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(json1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(json2))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        UserInDto updated = UserInDto.builder()
                .name("updatedName")
                .lastname("updatedLastname")
                .email("updatedEmail@mail.mm")
                .build();
        String json = objectMapper.writeValueAsString(updated);
        this.mockMvc.perform(put("/users/" + testUserId)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updatedName"))
                .andExpect(jsonPath("$.lastname").value("updatedLastname"));
    }

    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/" + testUserId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User successfully deleted."));

        this.mockMvc.perform(get("/users/" + testUserId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User id " + testUserId + " does not exist!"));
    }

    @Test
    void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[1].name").value("testName1"))
                .andExpect(jsonPath("$[2].name").value("testName2"));
    }

    @Test
    void changePasswordById() throws Exception {
        ChangePasswordInDto changePass = ChangePasswordInDto.builder()
                .email("updatedEmail@mail.mm")
                .oldPassword("1234Qwe#")
                .newPassword("567Asd%")
                .build();
        String json = objectMapper.writeValueAsString(changePass);

        this.mockMvc.perform(put("/users/pass")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password successfully changed"));
    }

    @Test
    void updateRestaurantOwner() throws Exception {
        UpdateRestaurantOwnerDto updateOwner = UpdateRestaurantOwnerDto.builder()
                .oldUserId(1L)
                .newUserId(2L)
                .build();
        String json = objectMapper.writeValueAsString(updateOwner);
        this.mockMvc.perform(put("/users/updateOwner")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.newOwner").value(updateOwner.getNewUserId()));
    }
}