package com.ruben.lotr.thelordofthering_api.exceptions;

import com.ruben.lotr.thelordofthering_api.exceptions.father.BaseDomainException;

public class HeroNotFoundException extends BaseDomainException {

    public HeroNotFoundException(Long id) {
        super(String.format("Hero <%d> not found.", id));
    }
}
