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
    private Double amountMilligrams;

    @Column(name = "daily_value_percent")
    private Double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">

    public Micromineral getMicromineral() {
        return micromineral;
    }

    public void setMicromineral(Micromineral micromineral) {
        this.micromineral = micromineral;
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
