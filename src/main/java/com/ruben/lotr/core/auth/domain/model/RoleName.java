package com.ruben.lotr.core.auth.domain.model;

public enum RoleName {
    ADMIN,
    USER;

    public static RoleName from(String rawValue) {
        return RoleName.valueOf(rawValue.trim().toUpperCase());
    }
}