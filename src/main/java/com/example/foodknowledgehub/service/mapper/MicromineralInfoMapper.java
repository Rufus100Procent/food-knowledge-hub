package com.example.foodknowledgehub.service.mapper;

import com.example.foodknowledgehub.dto.FoodSummaryDto;
import com.example.foodknowledgehub.dto.MicromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.MicromineralInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MicromineralInfoMapper {

    public MicromineralInfoDto toDto(MicromineralInfo info, List<Food> foods) {
        MicromineralInfoDto dto = new MicromineralInfoDto();
        dto.setMicromineral(info.getMicromineral().name());
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

