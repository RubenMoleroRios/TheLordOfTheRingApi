package com.ruben.lotr.thelordofthering_api.dto;

public class HeroSideDTO {

    private Long id;
    private String name;
    private String lastName;
    private String breedName;
    private String sideName;
    private String eyesColor;
    private String hairColor;
    private Double height;
    private String description;

    public HeroSideDTO(Long id, String name, String lastName, String breedName, String sideName, String eyesColor,
            String hairColor, Double height, String description) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.breedName = breedName;
        this.sideName = sideName;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.height = height;
        this.description = description;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getSideName() {
        return sideName;
    }

    public void setSideName(String sideName) {
        this.sideName = sideName;
    }

    public String geteyesColor() {
        return eyesColor;
    }

    public void seteyesColor(String eyesColor) {
        this.eyesColor = eyesColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}