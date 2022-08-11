package com.example.hw_5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        Hw5Application.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class Hw5ApplicationTests {
    @Test
    void contextLoads(){
    }
}
