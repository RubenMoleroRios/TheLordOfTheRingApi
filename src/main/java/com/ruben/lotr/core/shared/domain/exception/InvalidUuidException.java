package com.ruben.lotr.core.shared.domain.exception;

public class InvalidUuidException extends BaseDomainException {
    public InvalidUuidException(Class<?> clazz, String value) {
        super(String.format("The value <%s> is not a valid UUID for class <%s>",
                value, clazz.getSimpleName()));
    }
}