package com.example.foodknowledgehub.modal.miniral;

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
    private Double amountMilligrams;

    @Column(name = "daily_value_percent")
    private Double dailyValuePercent;

    //<editor-fold desc="Getters and Setters">

    public Macromineral getMacromineral() {
        return macromineral;
    }

    public void setMacromineral(Macromineral macromineral) {
        this.macromineral = macromineral;
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

