package com.example.foodknowledgehub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class MacromineralInfoDto {

    private String macromineral;

    @Size(max = 4000, message = "Overview cannot exceed 4000 characters.")
    private String overview;
    @Size(max = 50, message = "Benefits list cannot contain more than 50 items.")
    private List<
            @NotBlank(message = "Benefit text cannot be blank.")
            @Size(max = 255, message = "Benefit text cannot exceed 255 characters.")
                    String> benefits;

    @Size(max = 50, message = "Side effects list cannot contain more than 50 items.")
    private List<
            @Size(max = 255, message = "Side effects text cannot exceed 255 characters.")
                    String> sideEffects;

    @Size(max = 50, message = "DeficiencySigns list cannot contain more than 50 items.")
    private List<
            @Size(max = 255, message = "DeficiencySigns text cannot exceed 255 characters.")
                    String> deficiencySigns;

    private List<FoodSummaryDto> foods;
    private boolean verified = false;

    //<editor-fold desc="Getters and Setters">

    public String getMacromineral() {
        return macromineral;
    }

    public void setMacromineral(String macromineral) {
        this.macromineral = macromineral;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(List<String> sideEffects) {
        this.sideEffects = sideEffects;
    }

    public List<String> getDeficiencySigns() {
        return deficiencySigns;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setDeficiencySigns(List<String> deficiencySigns) {
        this.deficiencySigns = deficiencySigns;
    }

    public List<FoodSummaryDto> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodSummaryDto> foods) {
        this.foods = foods;
    }
    //</editor-fold>

}
