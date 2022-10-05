package com.example.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        UserServiceApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
