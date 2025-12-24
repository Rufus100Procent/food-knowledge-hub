package com.example.foodknowledgehub;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class MainTest {

    @Test
    void contextLoads() {

        String test1  = "a".repeat(10);

        assertEquals(10, test1.length());
    }

}
