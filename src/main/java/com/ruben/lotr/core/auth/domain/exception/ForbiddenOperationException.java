package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public final class ForbiddenOperationException extends BaseDomainException {

    public ForbiddenOperationException(String message) {
        super(message);
    }
}