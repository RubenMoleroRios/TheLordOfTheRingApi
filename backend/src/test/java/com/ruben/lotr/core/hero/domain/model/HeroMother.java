package com.ruben.lotr.core.hero.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.valueobject.hero.*;

public final class HeroMother {

    private HeroMother() {
    }

    public static Builder aHero() {
        return new Builder();
    }

    public static final class Builder {
        private @NonNull HeroIdVO id = HeroIdVOMother.random();
        private @NonNull Breed breed = Breed.unknown();
        private @NonNull Side side = Side.unknown();
        private @NonNull HeroNameVO name = HeroNameVOMother.aragorn();
        private @NonNull HeroLastNameVO lastName = HeroLastNameVOMother.defaultLastName();
        private @NonNull HeroEyesColorVO eyesColor = HeroEyesColorVOMother.unknown();
        private @NonNull HeroHairColorVO hairColor = HeroHairColorVOMother.unknown();
        private @NonNull HeroHeightVO height = HeroHeightVOMother.unknown();
        private @NonNull HeroDescriptionVO description = HeroDescriptionVOMother.empty();

        public Builder withId(@NonNull HeroIdVO id) {
            this.id = id;
            return this;
        }

        public Builder withBreed(@NonNull Breed breed) {
            this.breed = breed;
            return this;
        }

        public Builder withSide(@NonNull Side side) {
            this.side = side;
            return this;
        }

        public Builder withName(@NonNull HeroNameVO name) {
            this.name = name;
            return this;
        }

        public Builder withName(@NonNull String name) {
            return withName(HeroNameVO.create(name));
        }

        public Builder withLastName(@NonNull HeroLastNameVO lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withLastName(@NonNull String lastName) {
            return withLastName(HeroLastNameVO.create(lastName));
        }

        public Builder withEyesColor(@NonNull HeroEyesColorVO eyesColor) {
            this.eyesColor = eyesColor;
            return this;
        }

        public Builder withHairColor(@NonNull HeroHairColorVO hairColor) {
            this.hairColor = hairColor;
            return this;
        }

        public Builder withHeight(@NonNull HeroHeightVO height) {
            this.height = height;
            return this;
        }

        public Builder withDescription(@NonNull HeroDescriptionVO description) {
            this.description = description;
            return this;
        }

        public Hero buildNew() {
            return Hero.create(breed, side, name, lastName, eyesColor, hairColor, height, description);
        }

        public Hero buildPersisted() {
            return Hero.fromPersistence(id, breed, side, name, lastName, eyesColor, hairColor, height, description);
        }
    }
}
