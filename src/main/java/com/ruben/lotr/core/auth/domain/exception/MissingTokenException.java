package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class MissingTokenException extends BaseDomainException {

    public MissingTokenException() {
        super("Necesary token");
    }

}
