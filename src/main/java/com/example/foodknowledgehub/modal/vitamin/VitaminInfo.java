package com.example.foodknowledgehub.modal.vitamin;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vitamin_info")
public class VitaminInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Vitamin vitamin;

    @Column(length = 4000)
    private String overview;

    @Column(length = 4000)
    private List<String> benefits;

    @Column(length = 4000)
    private List<String> sideEffects;

    @Column(length = 4000)
    private List<String> deficiencySigns;

    private String imageUrl;

    private boolean verified = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vitamin getVitamin() {
        return vitamin;
    }

    public void setVitamin(Vitamin vitamin) {
        this.vitamin = vitamin;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
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

    public void setDeficiencySigns(List<String> deficiencySigns) {
        this.deficiencySigns = deficiencySigns;
    }
}
