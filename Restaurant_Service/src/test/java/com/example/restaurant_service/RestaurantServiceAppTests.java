package com.example.restaurant_service;

import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.dto.out.RestaurantOutDto;
import com.example.restaurant_service.entity.KitchenType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
        RestaurantServiceApp.class})
@ActiveProfiles("test")
public class RestaurantServiceAppTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void contextLoads() throws Exception {
        RestaurantInDto testRest = RestaurantInDto.builder()
                .name("testRest")
                .description("test")
                .email_address("test@test.rr")
                .phone_number("+79991122333")
                .kitchenType(KitchenType.AMERICAN)
                .build();
        String json = objectMapper.writeValueAsString(testRest);
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        RestaurantInDto testRest2 = RestaurantInDto.builder()
                .name("testRest2")
                .description("test2")
                .email_address("test2@test.rr")
                .phone_number("+79993322111")
                .kitchenType(KitchenType.ITALIAN)
                .build();
        String json2 = objectMapper.writeValueAsString(testRest2);
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON).content(json2))
                .andExpect(status().isOk());



        FeedbackInDto testFeedback1 = FeedbackInDto.builder()
                .restaurantId(1L)
                .feedback("testFeedback")
                .rating(3)
                .build();
        String json3 = objectMapper.writeValueAsString(testFeedback1);
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json3))
                .andExpect(status().isOk());

    }
}
