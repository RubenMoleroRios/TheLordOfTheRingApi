package com.ruben.lotr.core.auth.application.valueobject;

public record PlainPassword(String value) {

    public PlainPassword {
        if (value == null || value.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }
    }
}
