package com.ruben.lotr.core.shared.domain.exception;

public abstract class BaseDomainException extends RuntimeException {
    public BaseDomainException(String message) {
        super(message);
    }
}
