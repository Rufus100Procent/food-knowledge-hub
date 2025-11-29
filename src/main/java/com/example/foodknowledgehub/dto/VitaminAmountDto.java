package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class VitaminAmountDto {

    @NotNull(message = "Vitamin type is required.")
    private Vitamin vitamin;

    @DecimalMin(value = "0.0", message = "Vitamin amount cannot be negative.")
    @DecimalMax(value = "100000.0", message = "Vitamin amount is unrealistically high.")
    private double amountMilligrams;

    @DecimalMin(value = "0.0", message = "Daily value percent cannot be negative.")
    @DecimalMax(value = "1000.0", message = "Daily value percent cannot exceed 1000%.")
    private double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">

    public Vitamin getVitamin() {
        return vitamin;
    }

    public void setVitamin(Vitamin vitamin) {
        this.vitamin = vitamin;
    }

    public double getAmountMilligrams() {
        return amountMilligrams;
    }

    public void setAmountMilligrams(double amountMilligrams) {
        this.amountMilligrams = amountMilligrams;
    }

    public double getDailyValuePercent() {
        return dailyValuePercent;
    }

    public void setDailyValuePercent(double dailyValuePercent) {
        this.dailyValuePercent = dailyValuePercent;
    }

    //</editor-fold>
}
