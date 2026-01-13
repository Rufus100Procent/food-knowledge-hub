package com.example.foodknowledgehub.api;
//
//import com.example.foodknowledgehub.dto.FoodDto;
//import com.example.foodknowledgehub.service.FoodService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//class FoodControllerTest {
//
//    private FoodController controller;
//    private AutoCloseable closeable;
//    @Mock
//    private FoodService foodService;
//
//    @BeforeEach
//    void setup() {
//        closeable =  MockitoAnnotations.openMocks(this);
//        controller = new FoodController(foodService);
//    }
//
//    @Test
//    void createFood_returnCreatedFood() {
//        FoodDto dto  = new FoodDto("Apple");
//        FoodDto created  = new FoodDto("Apple");
//
//
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "file",
//                "test.txt",
//                MediaType.IMAGE_PNG_VALUE,
//                "test image!".getBytes()
//        );
//
//        when(foodService.createFood(dto, file)).thenReturn(created);
//        ResponseEntity<FoodDto> response = controller.createFood(dto, file);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assert response.getBody() != null;
//        assertEquals("Apple", response.getBody().getName());
//
//        verify(foodService).createFood(dto, file);
//    }
//
//    @Test
//    void getAllFood() {
//        FoodDto food = new FoodDto();
//        List<FoodDto> foods = List.of(food);
//
//        when(foodService.getAllFoods()).thenReturn(foods);
//
//        ResponseEntity<List<FoodDto>> response = controller.getAllFoods();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        assertEquals(food, response.getBody().getFirst());
//
//        verify(foodService).getAllFoods();
//    }
//
//    @Test
//    void getFood_returnFoodById() {
//        Long id = 1L;
//        FoodDto food = new FoodDto("Apple");
//        food.setId(id);
//
//        when(foodService.getFood(id)).thenReturn(food);
//
//        ResponseEntity<FoodDto> response = controller.getFood(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(id, response.getBody().getId());
//        assertEquals("Apple", response.getBody().getName());
//
//        verify(foodService).getFood(id);
//    }
//
//    @Test
//    void updateFood_withoutImage_returnUpdatedFood() {
//        Long id = 1L;
//        FoodDto dto = new FoodDto("Apple");
//
//        FoodDto updated = new FoodDto("Apple Updated");
//
//        when(foodService.updateFood(id, dto)).thenReturn(updated);
//
//        ResponseEntity<FoodDto> response =
//                controller.updateFood(id, dto, null);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("Apple Updated", response.getBody().getName());
//
//        verify(foodService).updateFood(id, dto);
//    }
//
//    @Test
//    void deleteFood_returnNoContent() {
//        Long id = 1L;
//
//        ResponseEntity<Void> response = controller.deleteFood(id);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertNull(response.getBody());
//
//        verify(foodService).deleteFood(id);
//    }
//
//    @AfterEach
//    void teardown() throws Exception {
//        closeable.close();
//    }
//}