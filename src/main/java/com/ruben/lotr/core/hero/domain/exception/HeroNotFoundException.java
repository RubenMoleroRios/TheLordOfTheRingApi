package com.ruben.lotr.core.hero.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class HeroNotFoundException extends BaseDomainException {

    public HeroNotFoundException(String id) {
        super(String.format("Hero <%s> not found.", id));
    }
}
