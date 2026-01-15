package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.FoodDto;
import com.example.foodknowledgehub.helper.FoodHelper;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.service.image.ImageService;
import com.example.foodknowledgehub.service.mapper.FoodMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FoodServiceTest {

    private FoodRepository foodRepository;
    private ImageService imageService;
    private FoodMapper foodMapper;

    private FoodService foodService;

    @BeforeEach
    void setUp() {
        foodRepository = mock(FoodRepository.class);
        imageService = mock(ImageService.class);
        foodMapper = new FoodMapper();

        foodService = new FoodService(
                foodRepository,
                foodMapper,
                imageService
        );
    }

    @Test
    void createFood_withoutImage_savesAndReturnsDto() {
        FoodDto dto = new FoodDto();
        dto.setName("Banana");

        Food savedFood = FoodHelper.fullBanana();
        savedFood.setId(1L);

        when(foodRepository.save(any(Food.class)))
                .thenReturn(savedFood);

        FoodDto result = foodService.createFood(dto, null);

        assertEquals(1L, result.getId());
        assertEquals("Banana", result.getName());

        verify(foodRepository).save(any(Food.class));
        verify(imageService, never()).storeImage(any(), any());
    }

    @Test
    void createFood_withImage_storesImageAndSetsUrl() {
        FoodDto dto = new FoodDto();
        dto.setName("Banana");

        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        when(imageService.storeImage(image, "food"))
                .thenReturn("food-123.png");

        Food saved = new Food();
        saved.setId(1L);
        saved.setName("Banana");
        saved.setImageUrl("food-123.png");

        when(foodRepository.save(any(Food.class)))
                .thenReturn(saved);

        FoodDto result = foodService.createFood(dto, image);

        assertEquals("food-123.png", result.getImageUrl());
        verify(imageService).storeImage(image, "food");
    }

    @Test
    void getFood_existingId_returnsDto() {
        Food food = FoodHelper.fullBanana();
        food.setId(10L);

        when(foodRepository.findById(10L))
                .thenReturn(Optional.of(food));

        FoodDto dto = foodService.getFood(10L);

        assertEquals("Banana", dto.getName());
        verify(foodRepository).findById(10L);
    }

    @Test
    void getFood_missingId_throwsException() {
        when(foodRepository.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> foodService.getFood(99L));

        assertTrue(ex.getMessage().contains("Food not found"));
    }

    @Test
    void updateFood_existingFood_updatesAndSaves() {
        Food existing = FoodHelper.fullBanana();
        existing.setId(5L);

        FoodDto update = new FoodDto();
        update.setName("Updated Banana");

        when(foodRepository.findById(5L))
                .thenReturn(Optional.of(existing));
        when(foodRepository.save(existing))
                .thenReturn(existing);

        FoodDto result = foodService.updateFood(5L, update);

        assertEquals("Updated Banana", result.getName());
        verify(foodRepository).save(existing);
    }

    @Test
    void updateFood_missingFood_throwsException() {
        when(foodRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> foodService.updateFood(1L, new FoodDto()));
    }

    @Test
    void deleteFood_existingFood_deletesImageAndEntity() {
        Food food = FoodHelper.fullBanana();
        food.setId(7L);
        food.setImageUrl("food-777.png");

        when(foodRepository.findById(7L))
                .thenReturn(Optional.of(food));

        foodService.deleteFood(7L);

        verify(imageService).deleteImage("food-777.png");
        verify(foodRepository).delete(food);
    }

    @Test
    void deleteFood_missingFood_throwsException() {
        when(foodRepository.findById(3L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> foodService.deleteFood(3L));
    }
}
