package com.ruben.lotr.core.auth.domain.exception;

import com.ruben.lotr.core.shared.domain.exception.BaseDomainException;

public class InvalidCredentialsException extends BaseDomainException {

    public InvalidCredentialsException() {
        super("Invalid credentials");
    }

}
