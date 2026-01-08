package com.ruben.lotr.core.shared.domain.valueobject;

import java.util.UUID;
import java.util.function.Function;

import com.ruben.lotr.core.shared.domain.exception.InvalidUuidException;

public abstract class UuidValueObject {
    protected final String value;

    protected UuidValueObject(String value) {
        this.value = value;
        ensureIsValidUuid(value);
    }

    protected static <T extends UuidValueObject> T generate(Function<String, T> creator) {
        return creator.apply(UUID.randomUUID().toString());
    }

    public final String value() {
        return value;
    }

    @Override
    public final boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        return value.equals(((UuidValueObject) other).value);
    }

    @Override
    public final int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    private void ensureIsValidUuid(String value) {
        if (value == null) {
            throw new InvalidUuidException(getClass(), value);
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidException(getClass(), value);
        }
    }

    protected static <T extends UuidValueObject> T create(
            String value,
            Function<String, T> creator) {
        return creator.apply(value);
    }
}