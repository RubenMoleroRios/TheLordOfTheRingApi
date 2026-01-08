package com.ruben.lotr.core.character.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class BreedNotFoundException extends BaseDomainException {

    public BreedNotFoundException(Long id) {
        super(String.format("Breed <%d> not found.", id));
    }
}
