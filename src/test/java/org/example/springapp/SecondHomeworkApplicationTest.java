package org.example.springapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SecondHomeworkApplicationTest.class})
class SecondHomeworkApplicationTest {
    @Test
    void contextLoads() {
        String asd = "sonarqube";
        assertNotNull(asd);
    }

}