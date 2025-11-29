package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.modal.miniral.Micromineral;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MicromineralAmountDto {

    @NotNull(message = "Micromineral type is required.")
    private Micromineral micromineral;

    @DecimalMin(value = "0.0", message = "Micromineral amount cannot be negative.")
    @DecimalMax(value = "100000.0", message = "Micromineral amount is unrealistically high.")
    private double amountMilligrams;

    @DecimalMin(value = "0.0", message = "Daily value percent cannot be negative.")
    @DecimalMax(value = "1000.0", message = "Daily value percent cannot exceed 1000%.")
    private double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">
    public Micromineral getMicromineral() {
        return micromineral;
    }

    public void setMicromineral(Micromineral micromineral) {
        this.micromineral = micromineral;
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
