package com.ruben.lotr.core.hero.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class BreedNotFoundException extends BaseDomainException {

    public BreedNotFoundException(String id) {
        super(String.format("Breed <%s> not found.", id));
    }
}
