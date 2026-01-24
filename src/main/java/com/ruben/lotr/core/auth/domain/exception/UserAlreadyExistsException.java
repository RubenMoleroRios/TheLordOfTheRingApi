package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public final class UserAlreadyExistsException extends BaseDomainException {

    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email);
    }
}
