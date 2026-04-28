package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public final class UserNotFoundException extends BaseDomainException {

    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }
}