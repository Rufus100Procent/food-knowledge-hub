package com.example.foodknowledgehub.modal.miniral;

import jakarta.persistence.Embeddable;

@Embeddable
public class MacronutrientProfile {

    private double calories;
    private double proteinGrams;
    private double fatGrams;
    private double carbohydratesGrams;
    private double fiberGrams;
    private double sugarGrams;

    //<editor-fold desc="Getters and Setters">

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteinGrams() {
        return proteinGrams;
    }

    public void setProteinGrams(double proteinGrams) {
        this.proteinGrams = proteinGrams;
    }

    public double getCarbohydratesGrams() {
        return carbohydratesGrams;
    }

    public void setCarbohydratesGrams(double carbohydratesGrams) {
        this.carbohydratesGrams = carbohydratesGrams;
    }

    public double getFatGrams() {
        return fatGrams;
    }

    public void setFatGrams(double fatGrams) {
        this.fatGrams = fatGrams;
    }

    public double getFiberGrams() {
        return fiberGrams;
    }

    public void setFiberGrams(double fiberGrams) {
        this.fiberGrams = fiberGrams;
    }

    public double getSugarGrams() {
        return sugarGrams;
    }

    public void setSugarGrams(double sugarGrams) {
        this.sugarGrams = sugarGrams;
    }
    //</editor-fold>
}
