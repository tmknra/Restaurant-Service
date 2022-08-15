package com.example.hw_5.controller;

import com.example.hw_5.Hw5ApplicationTests;
import com.example.hw_5.dto.in.RestaurantInDto;
import com.example.hw_5.dto.out.RestaurantOutDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ValidationException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RestaurantControllerTest extends Hw5ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addNewRestaurant() throws Exception {
        RestaurantInDto restaurantInDto = RestaurantInDto.builder()
                .name("testName")
                .description("testDescription")
                .phone_number("+79128761212")
                .email_address("asdasd@asd.qw")
                .build();

        RestaurantInDto emptyNameInDTO = RestaurantInDto.builder()
                .name("")
                .build();

        RestaurantInDto incorrectPhoneInDTO_1 = RestaurantInDto.builder()
                .phone_number("trghasd")
                .build();

        RestaurantInDto incorrectPhoneInDTO_2 = RestaurantInDto.builder()
                .phone_number("+79123871  ")
                .build();

        RestaurantInDto incorrectEmailInDTO = RestaurantInDto.builder()
                .name("test")
                .email_address("asdasd")
                .build();

        RestaurantOutDto restaurantOutDto = RestaurantOutDto.builder()
                .id(4L)
                .name("testName")
                .description("testDescription")
                .phone_number("+79128761212")
                .email_address("asdasd@asd.qw")
                .build();

        ObjectMapper objectMapper = new JsonMapper();
        String afterSaveRestaurant = objectMapper.writeValueAsString(restaurantOutDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/restaurants/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(restaurantInDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(afterSaveRestaurant));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/restaurants/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emptyNameInDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restaurants/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectPhoneInDTO_1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restaurants/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectPhoneInDTO_2)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restaurants/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectEmailInDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }
}
