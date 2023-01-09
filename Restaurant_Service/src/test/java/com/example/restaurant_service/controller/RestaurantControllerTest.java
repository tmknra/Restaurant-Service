package com.example.restaurant_service.controller;

import com.example.restaurant_service.RestaurantServiceAppTests;
import com.example.restaurant_service.dto.in.FeedbackInDto;
import com.example.restaurant_service.dto.in.RestaurantInDto;
import com.example.restaurant_service.dto.out.UserOutDto;
import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.feign.UserServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class RestaurantControllerTest extends RestaurantServiceAppTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    void createRestaurant() throws Exception {
        RestaurantInDto test = RestaurantInDto.builder().name("test").description("test").build();
        String json = objectMapper.writeValueAsString(test);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void getRestaurantById() throws Exception {
        RestaurantInDto test = RestaurantInDto.builder().name("getTestRestaurant").build();
        String json = objectMapper.writeValueAsString(test);

        String contentAsString = this.mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(contentAsString, Restaurant.class);

        this.mockMvc.perform(get("/restaurants/{id}", restaurant.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("getTestRestaurant"));
    }

    @Test
    void getAllRestaurants() throws Exception {
        this.mockMvc.perform(get("/restaurants/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSmallList() throws Exception {
        this.mockMvc.perform(get("/restaurants/smallList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateRestaurantById() throws Exception {
        RestaurantInDto preUpdatedTestRestaurant = RestaurantInDto.builder().name("preUpdatedTestRestaurant").build();
        String json = objectMapper.writeValueAsString(preUpdatedTestRestaurant);

        String contentAsString = this.mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(contentAsString, Restaurant.class);

        RestaurantInDto postUpdatedTestRestaurant = RestaurantInDto
                .builder()
                .name("postUpdatedTestRestaurant")
                .ownerId(null)
                .build();

        String jsonPost = objectMapper.writeValueAsString(postUpdatedTestRestaurant);
        Mockito.when(userServiceClient.getUser(null))
                .thenReturn(ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(new UserOutDto()));
        this.mockMvc.perform(put("/restaurants/{id}", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("postUpdatedTestRestaurant"));
    }

    @Test
    void deleteRestaurantById() throws Exception {
        RestaurantInDto deleteTestRestaurant = RestaurantInDto.builder()
                .name("deleteTestRestaurant")
                .build();
        String json = objectMapper.writeValueAsString(deleteTestRestaurant);

        String contentAsString = this.mockMvc
                .perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(contentAsString, Restaurant.class);

        this.mockMvc.perform(delete("/restaurants/{id}", restaurant.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/restaurants/{id}", restaurant.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFeedbacksByRestaurantId() throws Exception {
        RestaurantInDto getFeedbacksTest = RestaurantInDto.builder()
                .name("getFeedbacksTest")
                .build();
        String json = objectMapper.writeValueAsString(getFeedbacksTest);
        String contentAsString =
                this.mockMvc.perform(post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(contentAsString, Restaurant.class);
        FeedbackInDto testFeedback1 = FeedbackInDto.builder()
                .feedback("testFeedback1")
                .rating(5)
                .restaurantId(restaurant.getId())
                .build();
        FeedbackInDto testFeedback2 = FeedbackInDto.builder()
                .feedback("testFeedback2")
                .rating(2)
                .restaurantId(restaurant.getId())
                .build();

        String feed1 = objectMapper.writeValueAsString(testFeedback1);
        String feed2 = objectMapper.writeValueAsString(testFeedback2);

        this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(feed1))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(feed2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/restaurants/{id}/feedbacks", restaurant.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAverageRatingByRestaurantId() throws Exception {
        RestaurantInDto getFeedbacksTest = RestaurantInDto.builder()
                .name("getRatingTest")
                .build();
        String json = objectMapper.writeValueAsString(getFeedbacksTest);
        String contentAsString =
                this.mockMvc.perform(post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        Restaurant restaurant = objectMapper.readValue(contentAsString, Restaurant.class);
        FeedbackInDto testFeedback1 = FeedbackInDto.builder()
                .feedback("testFeedback1")
                .rating(5)
                .restaurantId(restaurant.getId())
                .build();
        FeedbackInDto testFeedback2 = FeedbackInDto.builder()
                .feedback("testFeedback2")
                .rating(2)
                .restaurantId(restaurant.getId())
                .build();

        String feed1 = objectMapper.writeValueAsString(testFeedback1);
        String feed2 = objectMapper.writeValueAsString(testFeedback2);

        this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(feed1))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON).content(feed2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/restaurants/{id}/rating", restaurant.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(3.5));
    }
}