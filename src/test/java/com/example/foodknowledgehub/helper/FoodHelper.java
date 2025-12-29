package com.example.foodknowledgehub.helper;

import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.*;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminAmount;

public final class FoodHelper {

    public static Food fullBanana() {
        Food banana = new Food();
        banana.setName("Banana");
        banana.setImageUrl("https://example.com/banana.png");
        banana.setOverview("Yellow potassium stick.");
        banana.getBenefits().add("High in potassium");
        banana.getBenefits().add("better sleep");

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

        return banana;
    }

    public static Food bananaWithPotassium() {
        Food food = new Food();
        food.setName("Banana");

        MacromineralAmount potassium = new MacromineralAmount();
        potassium.setMacromineral(Macromineral.POTASSIUM);
        food.getMacrominerals().add(potassium);

        return food;
    }

    public static Food milkWithCalcium() {
        Food food = new Food();
        food.setName("Milk");

        MacromineralAmount calcium = new MacromineralAmount();
        calcium.setMacromineral(Macromineral.CALCIUM);
        food.getMacrominerals().add(calcium);

        return food;
    }

    public static Food spinachWithIron() {
        Food food = new Food();
        food.setName("Spinach");

        MicromineralAmount iron = new MicromineralAmount();
        iron.setMicromineral(Micromineral.IRON);
        food.getMicrominerals().add(iron);

        return food;
    }

    public static Food apple() {
        Food food = new Food();
        food.setName("Apple");
        return food;
    }

    public static Food orangeWithVitaminC() {
        Food food = new Food();
        food.setName("Orange");

        VitaminAmount vitaminC = new VitaminAmount();
        vitaminC.setVitamin(Vitamin.C);
        food.getVitamins().add(vitaminC);

        return food;
    }

    public static Food bread() {
        Food food = new Food();
        food.setName("Bread");
        return food;
    }

    private FoodHelper() {
    }
}
