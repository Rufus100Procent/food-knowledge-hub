package com.example.foodknowledgehub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class FoodDto {

    @NotBlank(message = "Food name cannot be blank.")
    @Size(min = 1, max = 90 , message = "Food name must be between 1 and 100 characters.")
    private String name;

    @Size(max = 2048, message = "Image URL cannot exceed 2048 characters.")
    private String imageUrl;

    @Size(max = 4000, message = "Overview cannot exceed 4000 characters.")
    private String overview;

    @Size(max = 5, message = "Benefits list cannot contain more than 50 items.")
    private List<
            @NotBlank(message = "Benefit text cannot be blank.")
            @Size(max = 255, message = "Benefit text cannot exceed 255 characters.")
                    String>
            benefits;

    @Valid
    private MacronutrientProfileDto macronutrients;

    @Valid
    @Size(max = 50, message = "Macrominerals list cannot exceed 50 entries.")
    private List<MacromineralAmountDto> macrominerals;

    @Valid
    @Size(max = 50, message = "Microminerals list cannot exceed 50 entries.")
    private List<MicromineralAmountDto> microminerals;

    @Valid
    @Size(max = 50, message = "Vitamins list cannot exceed 50 entries.")
    private List<VitaminAmountDto> vitamins;


    public String getName() {
        return name;
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

    public MacronutrientProfileDto getMacronutrients() {
        return macronutrients;
    }

    public void setMacronutrients(MacronutrientProfileDto macronutrients) {
        this.macronutrients = macronutrients;
    }

    public List<MacromineralAmountDto> getMacrominerals() {
        return macrominerals;
    }

    public void setMacrominerals(List<MacromineralAmountDto> macrominerals) {
        this.macrominerals = macrominerals;
    }

    public List<MicromineralAmountDto> getMicrominerals() {
        return microminerals;
    }

    public void setMicrominerals(List<MicromineralAmountDto> microminerals) {
        this.microminerals = microminerals;
    }

    public List<VitaminAmountDto> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<VitaminAmountDto> vitamins) {
        this.vitamins = vitamins;
    }
}
