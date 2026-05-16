package com.ruben.lotr.core.auth.domain.model;

import org.springframework.lang.NonNull;

public final class Permission {

    private final String id;
    private final String name;

    private Permission(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static @NonNull Permission create(@NonNull String id, @NonNull String name) {
        return new Permission(id, name);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }
}