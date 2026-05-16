package com.ruben.lotr.core.shared.domain.valueobject;

public abstract class DoubleValueObject {
    protected final Double value;

    protected DoubleValueObject(Double value) {
        validate(value);
        this.value = value;
    }

    public Double value() {
        return value;
    }

    protected abstract void validate(Double value);
}
