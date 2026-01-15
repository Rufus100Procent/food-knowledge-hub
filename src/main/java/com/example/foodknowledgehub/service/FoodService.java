package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.*;
import com.example.foodknowledgehub.modal.*;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.service.image.ImageService;
import com.example.foodknowledgehub.service.mapper.FoodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodService.class);

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
        log.info("Creating new food");

        if (imageFile != null && !imageFile.isEmpty()) {
            log.debug("Image file provided for new food");
            dto.setImageFile(imageFile);
        }

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            String imagePath = imageService.storeImage(dto.getImageFile(), "food");
            dto.setImageUrl(imagePath);
        }

        Food food = foodMapper.toNewEntity(dto);
        Food saved = foodRepository.save(food);

        log.info("Food created with id {}", saved.getId());
        return foodMapper.toDto(saved);
    }

    public FoodDto updateFood(Long id, FoodDto dto) {
        log.info("Updating food with id {}", id);

        Food existing = foodRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Food not found with id {}", id);
                    return new IllegalArgumentException("cannot update food that doesnt exist: " + id);
                });

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {

            String imagePath = imageService.storeImage(dto.getImageFile(), "food");
            dto.setImageUrl(imagePath);
            log.info("New image stored at {}", imagePath);
        }

        foodMapper.updateEntity(dto, existing);
        Food saved = foodRepository.save(existing);

        log.info("Food updated with id {}", saved.getId());
        return foodMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public FoodDto getFood(Long id) {
        log.debug("Fetching food with id {}", id);

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Get by ID, Food not found with id {}", id);
                    return new IllegalArgumentException("Food not found: " + id);
                });
        return foodMapper.toDto(food);
    }

    @Transactional(readOnly = true)
    public List<FoodDto> getAllFoods() {
        log.debug("Fetching all foods");

        return foodRepository.findAll().stream()
                .map(foodMapper::toDto)
                .toList();
    }

    public void deleteFood(Long id) {
        log.info("Deleting food with id {}", id);

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));

        imageService.deleteImage(food.getImageUrl());
        log.info("Image file deleted at {}", food.getImageUrl());

        foodRepository.delete(food);
        log.info("Food deleted with id {}", id);

    }
}

