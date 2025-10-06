package com.ruben.lotr.thelordofthering_api.models;

public class Hero {

    private Long id, idBreed, idSide;
    private String name, lastName;
    private String eyesColor;
    private String hairColor;
    private Double height;
    private String description;
    private Breed breed;
    private Side side;

    public Hero(Long id, String name, String lastName, Long idBreed, Long idSide, String eyesColor, String hairColor,
            Double height, String description) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.idBreed = idBreed;
        this.idSide = idSide;
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

    public Long getIdBreed() {
        return idBreed;
    }

    public void setIdBreed(Long idBreed) {
        this.idBreed = idBreed;
    }

    public Long getIdSide() {
        return idSide;
    }

    public void setIdSide(Long idSide) {
        this.idSide = idSide;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public void setEyesColor(String eyesColor) {
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

}
