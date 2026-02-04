package com.ruben.lotr.core.hero.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class InvalidHeroHeightException extends BaseDomainException {

    public InvalidHeroHeightException(Double value) {
        super(String.format("Height must be positive, got: <%f>.", value));
    }

}
