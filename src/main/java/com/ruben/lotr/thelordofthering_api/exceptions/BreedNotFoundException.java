package com.ruben.lotr.thelordofthering_api.exceptions;

import com.ruben.lotr.thelordofthering_api.exceptions.father.BaseDomainException;

public class BreedNotFoundException extends BaseDomainException {

    public BreedNotFoundException(Long id) {
        super("Bando <" + id + "> no encontrado.");
    }
}
