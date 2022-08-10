package com.example.hw_5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {
        Hw5Application.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class Hw5ApplicationTests {
    @Test
    void contextLoads(){
    }
}
