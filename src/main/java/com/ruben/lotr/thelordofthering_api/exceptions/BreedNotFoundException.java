package com.ruben.lotr.thelordofthering_api.exceptions;

import com.ruben.lotr.thelordofthering_api.exceptions.father.BaseDomainException;

public class BreedNotFoundException extends BaseDomainException {

    public BreedNotFoundException(String id) {
        super(String.format("Breed <%s> not found.", id));
    }
}
