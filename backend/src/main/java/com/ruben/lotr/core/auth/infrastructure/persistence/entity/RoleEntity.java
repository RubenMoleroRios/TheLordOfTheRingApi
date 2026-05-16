package com.ruben.lotr.core.auth.infrastructure.persistence.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 40)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissions = new LinkedHashSet<>();

    protected RoleEntity() {
    }

    public RoleEntity(UUID id, String name, Set<PermissionEntity> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = new LinkedHashSet<>(permissions);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }
}