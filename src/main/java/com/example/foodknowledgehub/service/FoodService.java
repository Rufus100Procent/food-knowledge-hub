package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.*;
import com.example.foodknowledgehub.modal.*;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.service.image.ImageService;
import com.example.foodknowledgehub.service.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final ImageService imageService;

    public FoodService(FoodRepository foodRepository,
                       FoodMapper foodMapper,
                       ImageService imageService) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
        this.imageService = imageService;
    }

    public FoodDto createFood(FoodDto dto, MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty()) {
            dto.setImageFile(imageFile);
        }

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            String imagePath = imageService.storeImage(dto.getImageFile(),"food");
            dto.setImageUrl(imagePath);
        }

        Food food = foodMapper.toNewEntity(dto);
        Food saved = foodRepository.save(food);
        return foodMapper.toDto(saved);
    }

    public FoodDto updateFood(Long id, FoodDto dto) {
        Food existing = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            String imagePath = imageService.storeImage(dto.getImageFile(),"food");
            dto.setImageUrl(imagePath);
        }

        foodMapper.updateEntity(dto, existing);
        Food saved = foodRepository.save(existing);
        return foodMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public FoodDto getFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));
        return foodMapper.toDto(food);
    }

    @Transactional(readOnly = true)
    public List<FoodDto> getAllFoods() {
        return foodRepository.findAll().stream()
                .map(foodMapper::toDto)
                .toList();
    }

    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));

        String filePath = food.getImageUrl();
        foodRepository.delete(food);

        if (filePath != null) {
            Path path = Paths.get(filePath);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Failed to delete image file: " + filePath);
            }
        }
    }
}

