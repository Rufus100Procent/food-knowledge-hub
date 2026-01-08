package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.FoodDto;
import com.example.foodknowledgehub.service.FoodService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

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


        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.IMAGE_PNG_VALUE,
                "test image!".getBytes()
        );

        when(foodService.createFood(dto, file)).thenReturn(created);
        ResponseEntity<FoodDto> response = controller.createFood(dto, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("Apple", response.getBody().getName());

        verify(foodService).createFood(dto, file);
    }

    @Test
    void getAllFood() {
        FoodDto food = new FoodDto();
        List<FoodDto> foods = List.of(food);

        when(foodService.getAllFoods()).thenReturn(foods);

        ResponseEntity<List<FoodDto>> response = controller.getAllFoods();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(food, response.getBody().getFirst());

        verify(foodService).getAllFoods();
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }
}