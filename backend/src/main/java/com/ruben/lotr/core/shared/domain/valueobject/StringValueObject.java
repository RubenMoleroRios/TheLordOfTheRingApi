package com.ruben.lotr.core.shared.domain.valueobject;

import com.ruben.lotr.core.shared.domain.exception.InvalidStringLengthException;

public abstract class StringValueObject {

    protected static final int DEFAULT_MIN_LENGTH = 1;
    protected static final int DEFAULT_MAX_LENGTH = 255;

    protected final String value;

    protected StringValueObject(String value) {
        this.value = value == null ? null : value.trim();
        validate(this.value);
    }

    public String value() {
        return value;
    }

    protected abstract boolean allowNull();

    protected void validate(String value) {
        if (value == null) {
            if (allowNull()) {
                return;
            }
            throw new InvalidStringLengthException(minLength(), maxLength(), 0);
        }

        int length = value.length();
        if (length < minLength() || length > maxLength()) {
            throw new InvalidStringLengthException(minLength(), maxLength(), length);
        }
    }

    protected int minLength() {
        return DEFAULT_MIN_LENGTH;
    }

    protected int maxLength() {
        return DEFAULT_MAX_LENGTH;
    }
}
