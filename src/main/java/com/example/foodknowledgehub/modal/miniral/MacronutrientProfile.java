package com.example.foodknowledgehub.modal.miniral;

import jakarta.persistence.Embeddable;

@Embeddable
public class MacronutrientProfile {

    private Double calories;
    private Double proteinGrams;
    private Double fatGrams;
    private Double carbohydratesGrams;
    private Double fiberGrams;
    private Double sugarGrams;

    //<editor-fold desc="Getters and Setters">

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProteinGrams() {
        return proteinGrams;
    }

    public void setProteinGrams(Double proteinGrams) {
        this.proteinGrams = proteinGrams;
    }

    public Double getFatGrams() {
        return fatGrams;
    }

    public void setFatGrams(Double fatGrams) {
        this.fatGrams = fatGrams;
    }

    public Double getCarbohydratesGrams() {
        return carbohydratesGrams;
    }

    public void setCarbohydratesGrams(Double carbohydratesGrams) {
        this.carbohydratesGrams = carbohydratesGrams;
    }

    public Double getFiberGrams() {
        return fiberGrams;
    }

    public void setFiberGrams(Double fiberGrams) {
        this.fiberGrams = fiberGrams;
    }

    public Double getSugarGrams() {
        return sugarGrams;
    }

    public void setSugarGrams(Double sugarGrams) {
        this.sugarGrams = sugarGrams;
    }

    //</editor-fold>
}
