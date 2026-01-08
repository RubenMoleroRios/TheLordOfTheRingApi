package com.ruben.lotr.core.character.domain.model;

import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.domain.valueobject.BreedNameVO;

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
