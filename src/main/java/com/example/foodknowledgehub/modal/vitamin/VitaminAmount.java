package com.example.foodknowledgehub.modal.vitamin;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class VitaminAmount {

    @Enumerated(EnumType.STRING)
    @Column(name = "vitamin")
    private Vitamin vitamin;

    @Column(name = "amount_mg")
    private double amountMilligrams;

    @Column(name = "daily_value_percent")
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
