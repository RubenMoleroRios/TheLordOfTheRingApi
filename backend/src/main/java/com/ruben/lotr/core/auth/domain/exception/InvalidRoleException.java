package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public final class InvalidRoleException extends BaseDomainException {

    public InvalidRoleException(String role) {
        super("Invalid role: " + role);
    }
}