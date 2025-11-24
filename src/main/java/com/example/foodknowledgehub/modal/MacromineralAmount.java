package com.example.foodknowledgehub.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class MacromineralAmount {

    @Enumerated(EnumType.STRING)
    @Column(name = "macromineral")
    private Macromineral macromineral;

    @Column(name = "amount_mg")
    private double amountMilligrams;

    @Column(name = "daily_value_percent")
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

