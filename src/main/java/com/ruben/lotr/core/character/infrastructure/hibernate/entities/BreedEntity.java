package com.ruben.lotr.core.character.infrastructure.hibernate.entities;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "breed")
public class BreedEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    protected BreedEntity() {
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
