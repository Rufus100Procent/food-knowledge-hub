package com.example.foodknowledgehub.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class MacronutrientProfileDto {

    @DecimalMin(value = "0.0", message = "Calories cannot be negative.")
    @DecimalMax(value = "100000.0", message = "Calories value is unrealistically high.")
    private double calories;

    @DecimalMin(value = "0.0", message = "Protein cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Protein value is unrealistically high.")
    private double proteinGrams;

    @DecimalMin(value = "0.0", message = "Fat cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Fat value is unrealistically high.")
    private double fatGrams;

    @DecimalMin(value = "0.0", message = "Carbohydrates cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Carbohydrates value is unrealistically high.")
    private double carbohydratesGrams;

    @DecimalMin(value = "0.0", message = "Fiber cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Fiber value is unrealistically high.")
    private double fiberGrams;

    @DecimalMin(value = "0.0", message = "Sugar cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Sugar value is unrealistically high.")
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

    public double getFatGrams() {
        return fatGrams;
    }

    public void setFatGrams(double fatGrams) {
        this.fatGrams = fatGrams;
    }

    public double getCarbohydratesGrams() {
        return carbohydratesGrams;
    }

    public void setCarbohydratesGrams(double carbohydratesGrams) {
        this.carbohydratesGrams = carbohydratesGrams;
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
