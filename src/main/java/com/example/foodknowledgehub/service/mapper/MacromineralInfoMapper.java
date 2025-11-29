package com.example.foodknowledgehub.service.mapper;

import com.example.foodknowledgehub.dto.FoodSummaryDto;
import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MacromineralInfoMapper {

    public MacromineralInfoDto toDto(MacromineralInfo info, List<Food> foods) {
        MacromineralInfoDto dto = new MacromineralInfoDto();
        dto.setMacromineral(info.getMacromineral().name());
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

    public void applyDtoToEntity(MacromineralInfoDto dto, MacromineralInfo entity) {
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
