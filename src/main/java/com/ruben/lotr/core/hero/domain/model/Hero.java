package com.ruben.lotr.core.hero.domain.model;

import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroDescriptionVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHairColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHeightVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroLastNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroNameVO;

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
