package com.example.foodknowledgehub.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class MacronutrientProfileDto {

    @DecimalMin(value = "0.0", message = "Calories cannot be negative.")
    @DecimalMax(value = "100000.0", message = "Calories value is unrealistically high.")
    private Double calories;

    @DecimalMin(value = "0.0", message = "Protein cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Protein value is unrealistically high.")
    private Double proteinGrams;

    @DecimalMin(value = "0.0", message = "Fat cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Fat value is unrealistically high.")
    private Double fatGrams;

    @DecimalMin(value = "0.0", message = "Carbohydrates cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Carbohydrates value is unrealistically high.")
    private Double carbohydratesGrams;

    @DecimalMin(value = "0.0", message = "Fiber cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Fiber value is unrealistically high.")
    private Double fiberGrams;

    @DecimalMin(value = "0.0", message = "Sugar cannot be negative.")
    @DecimalMax(value = "10000.0", message = "Sugar value is unrealistically high.")
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
