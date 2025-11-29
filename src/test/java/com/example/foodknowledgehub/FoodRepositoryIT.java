package com.example.foodknowledgehub;


import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.modal.*;
import com.example.foodknowledgehub.modal.miniral.*;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminAmount;
import com.example.foodknowledgehub.repo.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class FoodRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    void saveAndLoadFood() {

        Food banana = new Food();
        banana.setName("Banana");
        banana.setImageUrl("https://example.com/banana.png");
        banana.setOverview("Yellow potassium stick.");
        banana.getBenefits().add("High in potassium");
        banana.getBenefits().add("Easy snack");

        MacronutrientProfile macros = new MacronutrientProfile();
        macros.setCalories(890);
        macros.setProteinGrams(1.1);
        macros.setFatGrams(0.3);
        macros.setCarbohydratesGrams(23);
        macros.setFiberGrams(2.6);
        macros.setSugarGrams(12);
        banana.setMacronutrients(macros);

        MacromineralAmount potassium = new MacromineralAmount();
        potassium.setMacromineral(Macromineral.POTASSIUM);
        potassium.setAmountMilligrams(358);
        potassium.setDailyValuePercent(10);
        banana.getMacrominerals().add(potassium);

        MicromineralAmount manganese = new MicromineralAmount();
        manganese.setMicromineral(Micromineral.MANGANESE);
        manganese.setAmountMilligrams(0.27);
        manganese.setDailyValuePercent(13);
        banana.getMicrominerals().add(manganese);

        VitaminAmount b6 = new VitaminAmount();
        b6.setVitamin(Vitamin.B6_PYRIDOXINE);
        b6.setAmountMilligrams(0.4);
        b6.setDailyValuePercent(20);
        banana.getVitamins().add(b6);

        Food saved = foodRepository.save(banana);

        Food found = foodRepository.findById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        assertEquals("Banana", found.getName());
        assertEquals("https://example.com/banana.png", found.getImageUrl());
        assertEquals("Yellow potassium stick.", found.getOverview());

        assertEquals(2, found.getBenefits().size());
        assertEquals("High in potassium", found.getBenefits().getFirst());

        assertEquals(890.0, found.getMacronutrients().getCalories());
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
}

