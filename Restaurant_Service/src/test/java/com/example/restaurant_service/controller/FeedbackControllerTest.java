package com.example.restaurant_service.controller;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class FeedbackControllerTest extends RestaurantServiceAppTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getFeedbackById() throws Exception {
        this.mockMvc.perform(get("/feedbacks/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.feedback").value("best of the best"))
                .andExpect(jsonPath("$.rating").value(5));

    }

    @Test
    void updateFeedbackById() throws Exception {
        FeedbackInDto feedbackInDto = FeedbackInDto.builder()
                .restaurantId(1L)
                .feedback("updated")
                .rating(5)
                .build();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(feedbackInDto);
        this.mockMvc.perform(put("/feedbacks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}