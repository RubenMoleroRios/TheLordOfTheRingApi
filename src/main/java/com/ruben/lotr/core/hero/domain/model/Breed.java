package com.ruben.lotr.core.hero.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;

public class Breed {

    private BreedIdVO id;
    private BreedNameVO name;

    private Breed(BreedIdVO id, BreedNameVO name) {
        this.id = id;
        this.name = name;
    }

    public static @NonNull Breed create(@NonNull BreedIdVO id, @NonNull BreedNameVO name) {
        return new Breed(id, name);
    }

    public static @NonNull Breed unknown() {
        return create(BreedIdVO.unknown(), BreedNameVO.unknown());
    }

    public BreedIdVO id() {
        return id;
    }

    public BreedNameVO name() {
        return name;
    }
}
