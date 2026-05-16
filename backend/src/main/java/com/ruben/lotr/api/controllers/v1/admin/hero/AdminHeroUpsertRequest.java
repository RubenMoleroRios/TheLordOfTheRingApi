package com.ruben.lotr.api.controllers.v1.admin.hero;

public record AdminHeroUpsertRequest(
                String name,
                String lastName,
                String eyesColor,
                String hairColor,
                Double height,
                String description,
                String breedId,
                String sideId) {
}