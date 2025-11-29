package com.example.foodknowledgehub.service.mapper;

import com.example.foodknowledgehub.dto.FoodSummaryDto;
import com.example.foodknowledgehub.dto.VitaminInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class VitaminInfoMapper {

    public VitaminInfoDto toDto(VitaminInfo info, List<Food> foods) {
        VitaminInfoDto dto = new VitaminInfoDto();
        dto.setVitamin(info.getVitamin().name());
        dto.setOverview(info.getOverview());
        dto.setBenefits(splitLines(info.getBenefits()));
        dto.setSideEffects(splitLines(info.getSideEffects()));
        dto.setDeficiencySigns(splitLines(info.getDeficiencySigns()));
        dto.setVerified(info.isVerified());

        if (foods != null) {
            List<FoodSummaryDto> foodDtos = new ArrayList<>();
            for (Food food : foods) {
                foodDtos.add(toFoodSummary(food));
            }
            dto.setFoods(foodDtos);
        }

        return dto;
    }

    public void applyDtoToEntity(VitaminInfoDto dto, VitaminInfo entity) {
        entity.setOverview(dto.getOverview());
        entity.setBenefits(joinLines(dto.getBenefits()));
        entity.setSideEffects(joinLines(dto.getSideEffects()));
        entity.setDeficiencySigns(joinLines(dto.getDeficiencySigns()));
        entity.setVerified(dto.isVerified());
    }

    private List<String> splitLines(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return Arrays.stream(text.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private String joinLines(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return String.join("\n", list);
    }

    private FoodSummaryDto toFoodSummary(Food food) {
        FoodSummaryDto dto = new FoodSummaryDto();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setImageUrl(food.getImageUrl());
        return dto;
    }
}
