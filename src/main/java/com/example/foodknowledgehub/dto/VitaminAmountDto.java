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
    private Double amountMilligrams;

    @DecimalMin(value = "0.0", message = "Daily value percent cannot be negative.")
    @DecimalMax(value = "1000.0", message = "Daily value percent cannot exceed 1000%.")
    private Double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">

    public Vitamin getVitamin() {
        return vitamin;
    }

    public void setVitamin(Vitamin vitamin) {
        this.vitamin = vitamin;
    }

    public Double getAmountMilligrams() {
        return amountMilligrams;
    }

    public void setAmountMilligrams(Double amountMilligrams) {
        this.amountMilligrams = amountMilligrams;
    }

    public Double getDailyValuePercent() {
        return dailyValuePercent;
    }

    public void setDailyValuePercent(Double dailyValuePercent) {
        this.dailyValuePercent = dailyValuePercent;
    }

    //</editor-fold>
}
