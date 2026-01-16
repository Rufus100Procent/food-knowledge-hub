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
        dto.setBenefits((info.getBenefits()));
        dto.setSideEffects((info.getSideEffects()));
        dto.setDeficiencySigns((info.getDeficiencySigns()));
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

    private FoodSummaryDto toFoodSummary(Food food) {
        FoodSummaryDto dto = new FoodSummaryDto();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setImageUrl(food.getImageUrl());
        return dto;
    }
}
