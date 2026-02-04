package com.ruben.lotr.core.hero.infrastructure.hibernate.entities;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "heroes")
public class HeroEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "eyes_color", length = 30)
    private String eyesColor;

    @Column(name = "hair_color", length = 30)
    private String hairColor;

    @Column(name = "height")
    private Double height;

    @Column(name = "description", length = 600)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_breed", nullable = false)
    private BreedEntity breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_side", nullable = false)
    private SideEntity side;

    protected HeroEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
