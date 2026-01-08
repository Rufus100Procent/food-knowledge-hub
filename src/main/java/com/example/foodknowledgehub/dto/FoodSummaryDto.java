package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.modal.miniral.MacromineralAmount;
import com.example.foodknowledgehub.modal.miniral.MicromineralAmount;
import com.example.foodknowledgehub.modal.vitamin.VitaminAmount;

import java.util.List;

public class FoodSummaryDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String overView;

    private List<String> benefits;

    private List<MacromineralAmount> macrominerals;
    private List<MicromineralAmount> microminerals;
    private List<VitaminAmount> vitamins;

    //<editor-fold desc="Getters and Setters">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView= overView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<MacromineralAmount> getMacrominerals() {
        return macrominerals;
    }

    public void setMacrominerals(List<MacromineralAmount> macrominerals) {
        this.macrominerals = macrominerals;
    }

    public List<MicromineralAmount> getMicrominerals() {
        return microminerals;
    }

    public void setMicrominerals(List<MicromineralAmount> microminerals) {
        this.microminerals = microminerals;
    }

    public List<VitaminAmount> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<VitaminAmount> vitamins) {
        this.vitamins = vitamins;
    }
//</editor-fold>

}

