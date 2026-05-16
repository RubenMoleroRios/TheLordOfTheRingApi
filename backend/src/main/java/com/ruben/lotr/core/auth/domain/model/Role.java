package com.ruben.lotr.core.auth.domain.model;

import java.util.List;

import org.springframework.lang.NonNull;

public final class Role {

    private final String id;
    private final String name;
    private final List<Permission> permissions;

    private Role(String id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = List.copyOf(permissions);
    }

    public static @NonNull Role create(
            @NonNull String id,
            @NonNull String name,
            @NonNull List<Permission> permissions) {
        return new Role(id, name, permissions);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public List<Permission> permissions() {
        return permissions;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(name);
    }
}