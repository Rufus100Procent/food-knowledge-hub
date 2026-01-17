package com.example.foodknowledgehub.repo;


import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.helper.FoodHelper;
import com.example.foodknowledgehub.modal.*;
import com.example.foodknowledgehub.modal.miniral.*;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class FoodRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    void saveAndLoadFood() {
        Food saved = foodRepository.save(FoodHelper.fullBanana());

        Food found = foodRepository.findById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        assertEquals("Banana", found.getName());
        assertEquals("https://example.com/banana.png", found.getImageUrl());
        assertEquals("Yellow potassium stick.", found.getOverview());

        assertEquals(2, found.getBenefits().size());
        assertEquals("High in potassium", found.getBenefits().getFirst());

        assertEquals(89.0, found.getMacronutrients().getCalories());
        assertEquals(23.0, found.getMacronutrients().getCarbohydratesGrams());

        assertEquals(1, found.getMacrominerals().size());
        assertEquals(Macromineral.POTASSIUM, found.getMacrominerals().getFirst().getMacromineral());
        assertEquals(358.0, found.getMacrominerals().getFirst().getAmountMilligrams());

        assertEquals(1, found.getMicrominerals().size());
        assertEquals(Micromineral.MANGANESE, found.getMicrominerals().getFirst().getMicromineral());
        assertEquals(0.27, found.getMicrominerals().getFirst().getAmountMilligrams());

        assertEquals(1, found.getVitamins().size());
        assertEquals(Vitamin.B6_PYRIDOXINE, found.getVitamins().getFirst().getVitamin());
        assertEquals(0.4, found.getVitamins().getFirst().getAmountMilligrams());
    }

    @Test
    void findDistinctByMacrominerals_Macromineral_returnsMatchingFoodsOnly() {
        foodRepository.saveAll(List.of(
                FoodHelper.bananaWithPotassium(),
                FoodHelper.milkWithCalcium()
        ));

        List<Food> result =
                foodRepository.findDistinctByMacrominerals_Macromineral(Macromineral.POTASSIUM);

        assertEquals(1, result.size());
        assertEquals("Banana", result.getFirst().getName());
    }

    @Test
    void findDistinctByMicrominerals_Micromineral_returnsMatchingFoodsOnly() {
        foodRepository.saveAll(List.of(
                FoodHelper.spinachWithIron(),
                FoodHelper.apple()
        ));

        List<Food> result =
                foodRepository.findDistinctByMicrominerals_Micromineral(Micromineral.IRON);

        assertEquals(1, result.size());
        assertEquals("Spinach", result.getFirst().getName());
    }

    @Test
    void findDistinctByVitamins_Vitamin_returnsMatchingFoodsOnly() {
        foodRepository.saveAll(List.of(
                FoodHelper.orangeWithVitaminC(),
                FoodHelper.bread()
        ));

        List<Food> result =
                foodRepository.findDistinctByVitamins_Vitamin(Vitamin.C);

        assertEquals(1, result.size());
        assertEquals("Orange", result.getFirst().getName());
    }
}
