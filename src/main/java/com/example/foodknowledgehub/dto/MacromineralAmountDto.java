package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.modal.Macromineral;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MacromineralAmountDto {

    @NotNull(message = "Macromineral type is required.")
    private Macromineral macromineral;

    @DecimalMin(value = "0.0", message = "Macromineral amount cannot be negative.")
    @DecimalMax(value = "100000.0", message = "Macromineral amount is unrealistically high.")
    private double amountMilligrams;

    @DecimalMin(value = "0.0", message = "Daily value percent cannot be negative.")
    @DecimalMax(value = "1000.0", message = "Daily value percent cannot exceed 1000%.")
    private double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">

    public Macromineral getMacromineral() {
        return macromineral;
    }

    public void setMacromineral(Macromineral macromineral) {
        this.macromineral = macromineral;
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
