package com.example.foodknowledgehub.modal;

import java.util.List;

public class Food {

    private Long id;
    private String name;
    private String imageUrl;
    private String overview;
    private List<String> benefits;

    private MacronutrientProfile macronutrients;

    private List<MacromineralAmount> macrominerals;
    private List<MicromineralAmount> microminerals;
    private List<VitaminAmount> vitamins;
}
