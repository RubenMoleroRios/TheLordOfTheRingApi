package com.ruben.lotr.core.hero.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVOMother;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVOMother;

public final class BreedMother {

    private BreedMother() {
    }

    public static Builder aBreed() {
        return new Builder();
    }

    public static final class Builder {
        private @NonNull BreedIdVO id = BreedIdVOMother.random();
        private @NonNull BreedNameVO name = BreedNameVOMother.human();

        public Builder withId(@NonNull BreedIdVO id) {
            this.id = id;
            return this;
        }

        public Builder withId(@NonNull String id) {
            return withId(BreedIdVO.create(id));
        }

        public Builder withName(@NonNull BreedNameVO name) {
            this.name = name;
            return this;
        }

        public Builder withName(@NonNull String name) {
            return withName(BreedNameVO.create(name));
        }

        public Breed build() {
            return Breed.create(id, name);
        }
    }
}
