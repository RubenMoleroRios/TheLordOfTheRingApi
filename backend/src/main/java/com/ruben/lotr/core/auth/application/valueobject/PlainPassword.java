package com.ruben.lotr.core.auth.application.valueobject;

public record PlainPassword(String value) {

    private static final int MIN_LENGTH = 8;

    public PlainPassword {
        if (value == null || value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Password too short");
        }
    }
}
