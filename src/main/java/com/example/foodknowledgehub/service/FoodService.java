package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.*;
import com.example.foodknowledgehub.modal.*;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.service.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public FoodService(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    public FoodDto createFood(FoodDto dto) {
        Food food = foodMapper.toNewEntity(dto);
        Food saved = foodRepository.save(food);
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

    public FoodDto updateFood(Long id, FoodDto dto) {
        Food existing = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));

        foodMapper.updateEntity(dto, existing);

        Food saved = foodRepository.save(existing);
        return foodMapper.toDto(saved);
    }

    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new IllegalArgumentException("Food not found: " + id);
        }
        foodRepository.deleteById(id);
    }
}
