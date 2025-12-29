package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.FoodDto;
import com.example.foodknowledgehub.service.FoodService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class FoodControllerTest {

    private FoodController controller;
    private AutoCloseable closeable;
    @Mock
    private FoodService foodService;

    @BeforeEach
    void setup() {
        closeable =  MockitoAnnotations.openMocks(this);
        controller = new FoodController(foodService);
    }

    @Test
    void createFood_returnCreatedFood() {
        FoodDto dto  = new FoodDto("Apple");
        FoodDto created  = new FoodDto("Apple");

        when(foodService.createFood(dto)).thenReturn(created);

        ResponseEntity<FoodDto> response = controller.createFood(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Apple", response.getBody().getName());

        verify(foodService).createFood(dto);
    }

    @Test
    void getFood() {
    }

    @Test
    void getAllFoods() {
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }
}