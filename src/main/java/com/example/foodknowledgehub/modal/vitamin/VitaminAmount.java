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
    private Double amountMilligrams;

    @Column(name = "daily_value_percent")
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
