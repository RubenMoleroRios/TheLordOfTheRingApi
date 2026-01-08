package com.ruben.lotr.core.character.domain.model;

import com.ruben.lotr.core.character.domain.valueobject.HeroDescriptionVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroEyesColorVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroHairColorVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroHeightVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroLastNameVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroNameVO;

public class Hero {

    private HeroIdVO id;
    private HeroNameVO name;
    private HeroLastNameVO lastName;
    private HeroEyesColorVO eyesColor;
    private HeroHairColorVO hairColor;
    private HeroHeightVO height;
    private HeroDescriptionVO description;
    private Breed breed;
    private Side side;

    public Hero(
            HeroIdVO id,
            Breed breed,
            Side side,
            HeroNameVO name,
            HeroLastNameVO lastName,
            HeroEyesColorVO eyesColor,
            HeroHairColorVO hairColor,
            HeroHeightVO height,
            HeroDescriptionVO description) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.breed = breed;
        this.side = side;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.height = height;
        this.description = description;
    }

    public static Hero create(
            HeroIdVO id,
            Breed breed,
            Side side,
            HeroNameVO name,
            HeroLastNameVO lastName,
            HeroEyesColorVO eyesColor,
            HeroHairColorVO hairColor,
            HeroHeightVO height,
            HeroDescriptionVO description) {
        return new Hero(id, breed, side, name, lastName, eyesColor, hairColor, height, description);
    }

    public HeroIdVO id() {
        return id;
    }

    public HeroNameVO name() {
        return name;
    }

    public HeroLastNameVO lastName() {
        return lastName;
    }

    public HeroEyesColorVO eyesColor() {
        return eyesColor;
    }

    public HeroHairColorVO hairColor() {
        return hairColor;
    }

    public HeroHeightVO height() {
        return height;
    }

    public HeroDescriptionVO description() {
        return description;
    }

    public Breed breed() {
        return breed;
    }

    public Side side() {
        return side;
    }

}
