package com.ruben.lotr.core.hero.application.usecase;

public record AdminCreateHeroCommand(
                String name,
                String lastName,
                String eyesColor,
                String hairColor,
                Double height,
                String description,
                String breedId,
                String sideId) {
}