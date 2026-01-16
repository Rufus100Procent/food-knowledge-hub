package com.example.foodknowledgehub.modal.miniral;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "micromineral_info")
public class MicromineralInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Micromineral micromineral;

    @Column(length = 4000)
    private String overview;

    @Column(length = 4000)
    private List<String> benefits;

    @Column(length = 4000)
    private List<String> sideEffects;

    @Column(length = 4000)
    private List<String> deficiencySigns;

    private boolean verified = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Micromineral getMicromineral() {
        return micromineral;
    }

    public void setMicromineral(Micromineral micromineral) {
        this.micromineral = micromineral;
    }

    public String getOverview() {
        return overview;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
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

    public void setDeficiencySigns(List<String> deficiencySigns) {
        this.deficiencySigns = deficiencySigns;
    }
}
