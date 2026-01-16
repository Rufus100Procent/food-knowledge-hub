package com.example.foodknowledgehub.service.mapper;

import com.example.foodknowledgehub.dto.FoodSummaryDto;
import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MacromineralInfoMapper {

    public MacromineralInfoDto toDto(MacromineralInfo info, List<Food> foods) {
        MacromineralInfoDto dto = new MacromineralInfoDto();
        dto.setMacromineral(info.getMacromineral().name());
        dto.setOverview(info.getOverview());
        dto.setBenefits((info.getBenefits()));
        dto.setSideEffects((info.getSideEffects()));
        dto.setDeficiencySigns((info.getDeficiencySigns()));
        dto.setVerified(info.isVerified());
        dto.setImageUrl(info.getImageUrl());

        if (foods != null) {
            List<FoodSummaryDto> foodDtos = new ArrayList<>();
            for (Food food : foods) {
                foodDtos.add(toFoodSummary(food));
            }
            dto.setFoods(foodDtos);
        }

        return dto;
    }

    private FoodSummaryDto toFoodSummary(Food food) {
        FoodSummaryDto dto = new FoodSummaryDto();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setImageUrl(food.getImageUrl());
        return dto;
    }
}
