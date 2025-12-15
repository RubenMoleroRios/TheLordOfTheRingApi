package com.ruben.lotr.thelordofthering_api.exceptions;

import com.ruben.lotr.thelordofthering_api.exceptions.father.BaseDomainException;

public class SideNotFoundException extends BaseDomainException {

    public SideNotFoundException(Long id) {
        super("Raza <" + id + "> no encontrada.");
    }
}
