package com.example.restaurant_service.controller;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.FeedbackOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class FeedbackControllerTest extends RestaurantServiceAppTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Long restaurantId;

    @BeforeEach
    void setUp() throws Exception {
        RestaurantInDto test = RestaurantInDto.builder().name("test").description("test").build();
        String json = objectMapper.writeValueAsString(test);
        String contentAsString = this.mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        restaurantId = objectMapper.readValue(contentAsString, Restaurant.class).getId();
    }

    @Test
    void addFeedbackByRestaurantId() throws Exception {
        FeedbackInDto testFeed = FeedbackInDto.builder()
                .feedback("testFeed")
                .rating(5)
                .restaurantId(restaurantId)
                .build();

        String json = objectMapper.writeValueAsString(testFeed);
        this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feedback").value("testFeed"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.restaurantId").value(restaurantId));
    }

    @Test
    void getFeedbackById() throws Exception {
        FeedbackInDto testFeed = FeedbackInDto.builder()
                .feedback("getTestFeed")
                .rating(2)
                .restaurantId(restaurantId)
                .build();

        String json = objectMapper.writeValueAsString(testFeed);
        String contentAsString = this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FeedbackOutDto feedback = objectMapper.readValue(contentAsString, FeedbackOutDto.class);

        this.mockMvc.perform(get("/feedbacks/{id}", feedback.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(restaurantId))
                .andExpect(jsonPath("$.feedback").value("getTestFeed"))
                .andExpect(jsonPath("$.rating").value(2));

    }

    @Test
    void updateFeedbackById() throws Exception {
        FeedbackInDto preUpdated = FeedbackInDto.builder()
                .restaurantId(restaurantId)
                .feedback("preUpdated")
                .rating(1)
                .build();
        String json = objectMapper.writeValueAsString(preUpdated);
        String contentAsString = this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FeedbackOutDto feedback = objectMapper.readValue(contentAsString, FeedbackOutDto.class);

        FeedbackInDto postUpdated = FeedbackInDto.builder()
                .restaurantId(restaurantId)
                .feedback("postUpdated")
                .rating(5)
                .build();
        String update = objectMapper.writeValueAsString(postUpdated);
        this.mockMvc.perform(put("/feedbacks/{id}", feedback.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(update))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedback").value("postUpdated"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void deleteFeedbackById() throws Exception {
        FeedbackInDto deleteTest = FeedbackInDto.builder()
                .restaurantId(restaurantId)
                .feedback("preUpdated")
                .rating(1)
                .build();
        String json = objectMapper.writeValueAsString(deleteTest);
        String contentAsString = this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FeedbackOutDto feedback = objectMapper.readValue(contentAsString, FeedbackOutDto.class);
        this.mockMvc.perform(delete("/feedbacks/{id}", feedback.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/feedbacks/{id}", feedback.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}