package com.ruben.lotr.core.character.application.response.dto;

public class HeroDTO {
    private String id;
    private String name;
    private String lastName;
    private String breedName;
    private String sideName;
    private String eyesColor;
    private String hairColor;
    private Double height;
    private String description;

    public HeroDTO(
            String id, String name, String lastName,
            String breedName, String sideName,
            String eyesColor, String hairColor,
            Double height, String description) {
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBreedName() {
        return breedName;
    }

    public String getSideName() {
        return sideName;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public Double getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }
}
