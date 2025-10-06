package com.ruben.lotr.thelordofthering_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "heroes")
public class HeroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String eyesColor;
    private String hairColor;
    private Double height;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_breed", referencedColumnName = "id", insertable = false, updatable = false)
    private BreedEntity breed;
    @ManyToOne
    @JoinColumn(name = "id_side", referencedColumnName = "id", insertable = false, updatable = false)
    private SideEntity side;

    public HeroEntity() {
    }

    public HeroEntity(Long id, String name, String lastName, String eyesColor,
            String hairColor, Double height, String description, BreedEntity breed, SideEntity side) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.height = height;
        this.description = description;
        this.breed = breed;
        this.side = side;
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

    public BreedEntity getBreed() {
        return breed;
    }

    public void setBreed(BreedEntity breed) {
        this.breed = breed;
    }

    public SideEntity getSide() {
        return side;
    }

    public void setSide(SideEntity side) {
        this.side = side;
    }

}
