package com.ruben.lotr.core.hero.infrastructure.hibernate.entities;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "side")
public class SideEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    protected SideEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
