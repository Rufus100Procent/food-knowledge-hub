package com.example.foodknowledgehub.dto;

public class FoodSummaryDto {
    private Long id;
    private String name;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    //<editor-fold desc="Getters and Setters">

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

    //</editor-fold>

}

