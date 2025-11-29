package com.example.foodknowledgehub.modal.miniral;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class MicromineralAmount {

    @Enumerated(EnumType.STRING)
    @Column(name = "micromineral")
    private Micromineral micromineral;

    @Column(name = "amount_mg")
    private double amountMilligrams;

    @Column(name = "daily_value_percent")
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
