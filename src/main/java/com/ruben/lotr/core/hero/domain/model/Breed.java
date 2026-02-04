package com.ruben.lotr.core.hero.domain.model;

import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;

public class Breed {

    private BreedIdVO id;
    private BreedNameVO name;

    private Breed(BreedIdVO id, BreedNameVO name) {
        this.id = id;
        this.name = name;
    }

    public static Breed create(BreedIdVO id, BreedNameVO name) {
        return new Breed(id, name);
    }

    public static Breed unknown() {
        return create(BreedIdVO.unknown(), BreedNameVO.unknown());
    }

    public BreedIdVO id() {
        return id;
    }

    public BreedNameVO name() {
        return name;
    }
}
