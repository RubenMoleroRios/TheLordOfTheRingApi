package com.ruben.lotr.core.character.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class SideNotFoundException extends BaseDomainException {

    public SideNotFoundException(String id) {
        super(String.format("Side <%s> not found.", id));
    }
}
