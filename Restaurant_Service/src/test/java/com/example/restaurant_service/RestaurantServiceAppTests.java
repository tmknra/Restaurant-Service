package com.example.restaurant_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        RestaurantServiceApp.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class RestaurantServiceAppTests {
    @Test
    void contextLoads(){
    }
}
