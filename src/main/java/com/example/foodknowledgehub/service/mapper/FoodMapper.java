package com.example.foodknowledgehub.service.mapper;
import com.example.foodknowledgehub.dto.*;
import com.example.foodknowledgehub.modal.*;

import com.example.foodknowledgehub.modal.miniral.MacromineralAmount;
import com.example.foodknowledgehub.modal.miniral.MacronutrientProfile;
import com.example.foodknowledgehub.modal.miniral.MicromineralAmount;
import com.example.foodknowledgehub.modal.vitamin.VitaminAmount;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FoodMapper {

    public Food toNewEntity(FoodDto dto) {
        Food food = new Food();
        applyDtoToEntity(dto, food);
        return food;
    }

    public void updateEntity(FoodDto dto, Food food) {
        // Only update non-null fields from DTO
        applyDtoToEntitySelectively(dto, food);
    }

    public FoodDto toDto(Food food) {
        FoodDto dto = new FoodDto();

        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setImageUrl(food.getImageUrl());
        dto.setOverview(food.getOverview());

        if (food.getBenefits() != null) {
            dto.setBenefits(new ArrayList<>(food.getBenefits()));
        }

        if (food.getMacronutrients() != null) {
            MacronutrientProfile macros = food.getMacronutrients();
            MacronutrientProfileDto m = new MacronutrientProfileDto();
            m.setCalories(macros.getCalories());
            m.setProteinGrams(macros.getProteinGrams());
            m.setFatGrams(macros.getFatGrams());
            m.setCarbohydratesGrams(macros.getCarbohydratesGrams());
            m.setFiberGrams(macros.getFiberGrams());
            m.setSugarGrams(macros.getSugarGrams());
            dto.setMacronutrients(m);
        }

        if (food.getMacrominerals() != null) {
            List<MacromineralAmountDto> mmDtos = new ArrayList<>();
            for (MacromineralAmount mm : food.getMacrominerals()) {
                MacromineralAmountDto mmDto = new MacromineralAmountDto();
                mmDto.setMacromineral(mm.getMacromineral());
                mmDto.setAmountMilligrams(mm.getAmountMilligrams());
                mmDto.setDailyValuePercent(mm.getDailyValuePercent());
                mmDtos.add(mmDto);
            }
            dto.setMacrominerals(mmDtos);
        }

        if (food.getMicrominerals() != null) {
            List<MicromineralAmountDto> micDtos = new ArrayList<>();
            for (MicromineralAmount mic : food.getMicrominerals()) {
                MicromineralAmountDto micDto = new MicromineralAmountDto();
                micDto.setMicromineral(mic.getMicromineral());
                micDto.setAmountMilligrams(mic.getAmountMilligrams());
                micDto.setDailyValuePercent(mic.getDailyValuePercent());
                micDtos.add(micDto);
            }
            dto.setMicrominerals(micDtos);
        }

        if (food.getVitamins() != null) {
            List<VitaminAmountDto> vDtos = new ArrayList<>();
            for (VitaminAmount v : food.getVitamins()) {
                VitaminAmountDto vDto = new VitaminAmountDto();
                vDto.setVitamin(v.getVitamin());
                vDto.setAmountMilligrams(v.getAmountMilligrams());
                vDto.setDailyValuePercent(v.getDailyValuePercent());
                vDtos.add(vDto);
            }
            dto.setVitamins(vDtos);
        }

        return dto;
    }

    private void applyDtoToEntity(FoodDto dto, Food food) {
        food.setName(dto.getName());
        food.setImageUrl(dto.getImageUrl());
        food.setOverview(dto.getOverview());

        food.getBenefits().clear();
        if (dto.getBenefits() != null) {
            food.getBenefits().addAll(dto.getBenefits());
        }

        applyMacronutrients(dto, food);
        applyMacrominerals(dto, food);
        applyMicrominerals(dto, food);
        applyVitamins(dto, food);
    }

    private void applyDtoToEntitySelectively(FoodDto dto, Food food) {
        if (dto.getName() != null) {
            food.setName(dto.getName());
        }

        if (dto.getImageUrl() != null) {
            food.setImageUrl(dto.getImageUrl());
        }

        if (dto.getOverview() != null) {
            food.setOverview(dto.getOverview());
        }

        if (dto.getBenefits() != null) {
            food.getBenefits().clear();
            food.getBenefits().addAll(dto.getBenefits());
        }

        if (dto.getMacronutrients() != null) {
            applyMacronutrientsSelectively(dto.getMacronutrients(), food);
        }

        if (dto.getMacrominerals() != null) {
            applyMacrominerals(dto, food);
        }

        if (dto.getMicrominerals() != null) {
            applyMicrominerals(dto, food);
        }

        if (dto.getVitamins() != null) {
            applyVitamins(dto, food);
        }
    }

    private void applyMacronutrients(FoodDto dto, Food food) {
        MacronutrientProfileDto m = dto.getMacronutrients();

        if (m == null) {
            food.setMacronutrients(null);
            return;
        }

        MacronutrientProfile macros = food.getMacronutrients();
        if (macros == null) {
            macros = new MacronutrientProfile();
            food.setMacronutrients(macros);
        }

        macros.setCalories(m.getCalories());
        macros.setProteinGrams(m.getProteinGrams());
        macros.setFatGrams(m.getFatGrams());
        macros.setCarbohydratesGrams(m.getCarbohydratesGrams());
        macros.setFiberGrams(m.getFiberGrams());
        macros.setSugarGrams(m.getSugarGrams());
    }

    private void applyMacronutrientsSelectively(MacronutrientProfileDto m, Food food) {
        MacronutrientProfile macros = food.getMacronutrients();
        if (macros == null) {
            macros = new MacronutrientProfile();
            food.setMacronutrients(macros);
        }

        if (m.getCalories() != null) {
            macros.setCalories(m.getCalories());
        }
        if (m.getProteinGrams() != null) {
            macros.setProteinGrams(m.getProteinGrams());
        }
        if (m.getFatGrams() != null) {
            macros.setFatGrams(m.getFatGrams());
        }
        if (m.getCarbohydratesGrams() != null) {
            macros.setCarbohydratesGrams(m.getCarbohydratesGrams());
        }
        if (m.getFiberGrams() != null) {
            macros.setFiberGrams(m.getFiberGrams());
        }
        if (m.getSugarGrams() != null) {
            macros.setSugarGrams(m.getSugarGrams());
        }
    }

    private void applyMacrominerals(FoodDto dto, Food food) {
        food.getMacrominerals().clear();
        if (dto.getMacrominerals() == null) {
            return;
        }

        for (MacromineralAmountDto mmDto : dto.getMacrominerals()) {
            MacromineralAmount mm = new MacromineralAmount();
            mm.setMacromineral(mmDto.getMacromineral());
            mm.setAmountMilligrams(mmDto.getAmountMilligrams());
            mm.setDailyValuePercent(mmDto.getDailyValuePercent());
            food.getMacrominerals().add(mm);
        }
    }

    private void applyMicrominerals(FoodDto dto, Food food) {
        food.getMicrominerals().clear();
        if (dto.getMicrominerals() == null) {
            return;
        }

        for (MicromineralAmountDto micDto : dto.getMicrominerals()) {
            MicromineralAmount mic = new MicromineralAmount();
            mic.setMicromineral(micDto.getMicromineral());
            mic.setAmountMilligrams(micDto.getAmountMilligrams());
            mic.setDailyValuePercent(micDto.getDailyValuePercent());
            food.getMicrominerals().add(mic);
        }
    }

    private void applyVitamins(FoodDto dto, Food food) {
        food.getVitamins().clear();
        if (dto.getVitamins() == null) {
            return;
        }

        for (VitaminAmountDto vDto : dto.getVitamins()) {
            VitaminAmount v = new VitaminAmount();
            v.setVitamin(vDto.getVitamin());
            v.setAmountMilligrams(vDto.getAmountMilligrams());
            v.setDailyValuePercent(vDto.getDailyValuePercent());
            food.getVitamins().add(v);
        }
    }
}
