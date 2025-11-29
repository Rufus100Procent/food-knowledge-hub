package com.example.foodknowledgehub.modal;

import com.example.foodknowledgehub.modal.miniral.MacromineralAmount;
import com.example.foodknowledgehub.modal.miniral.MacronutrientProfile;
import com.example.foodknowledgehub.modal.miniral.MicromineralAmount;
import com.example.foodknowledgehub.modal.vitamin.VitaminAmount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private String imageUrl;

    @Column(length = 4000)
    private String overview;

    @ElementCollection
    @CollectionTable(
            name = "food_benefit",
            joinColumns = @JoinColumn(name = "food_id")
    )
    @Column(name = "benefit")
    private List<String> benefits = new ArrayList<>();

    @Embedded
    private MacronutrientProfile macronutrients;

    @ElementCollection
    @CollectionTable(
            name = "food_macromineral",
            joinColumns = @JoinColumn(name = "food_id")
    )
    private List<MacromineralAmount> macrominerals = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "food_micromineral",
            joinColumns = @JoinColumn(name = "food_id")
    )
    private List<MicromineralAmount> microminerals = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "food_vitamin",
            joinColumns = @JoinColumn(name = "food_id")
    )
    private List<VitaminAmount> vitamins = new ArrayList<>();

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

    public MacronutrientProfile getMacronutrients() {
        return macronutrients;
    }

    public void setMacronutrients(MacronutrientProfile macronutrients) {
        this.macronutrients = macronutrients;
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