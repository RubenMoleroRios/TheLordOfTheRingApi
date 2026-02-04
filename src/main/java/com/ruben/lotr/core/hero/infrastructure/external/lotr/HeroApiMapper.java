package com.ruben.lotr.core.hero.infrastructure.external.lotr;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ruben.lotr.core.hero.domain.model.Breed;
import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.model.Side;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroDescriptionVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroEyesColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHairColorVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroHeightVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroLastNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroNameVO;
import com.ruben.lotr.core.hero.infrastructure.external.lotr.client.LotrHeroResponseApiDTO;
import com.ruben.lotr.core.shared.util.NumberUtils;

@Component
public class HeroApiMapper {

    private final Map<String, Double> differentSizeMap = Map.of(
            "Very tall", 3.0,
            "Tall", 2.0,
            "Tallest of the Elves of Gondolin", 2.5,
            "Short", 1.0);

    private final int heightCmToM = 100;

    public Hero toDomain(
            LotrHeroResponseApiDTO dto,
            HeroIdVO heroId,
            Map<String, String> breedIdMap) {

        String[] nameParts = dto.getName().split(" ", 2);

        return Hero.create(
                heroId,
                toBreed(dto.getRace(), breedIdMap),
                Side.unknown(),
                HeroNameVO.create(nameParts[0]),
                nameParts.length > 1 ? HeroLastNameVO.create(nameParts[1]) : HeroLastNameVO.unknown(),
                HeroEyesColorVO.unknown(),
                dto.getHair() != null && !dto.getHair().isBlank()
                        ? HeroHairColorVO.create(dto.getHair())
                        : HeroHairColorVO.unknown(),
                HeroHeightVO.create(parseHeight(dto.getHeight())),
                HeroDescriptionVO.unknown());
    }

    private Breed toBreed(String race, Map<String, String> breedIdMap) {
        return getBreedIdByRace(race, breedIdMap)
                .map(id -> Breed.create(BreedIdVO.create(id), BreedNameVO.create(race)))
                .orElse(Breed.unknown());
    }

    private Optional<String> getBreedIdByRace(String race, Map<String, String> breedIdMap) {
        return breedIdMap.entrySet().stream()
                .filter(e -> e.getValue().equals(race))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private Double parseHeight(String height) {
        if (height == null || height.isEmpty())
            return null;

        if (NumberUtils.isDouble(height.split("cm")[0]))
            return Double.parseDouble(height.split("cm")[0]) / heightCmToM;

        if (NumberUtils.isDouble(height.split("m")[0]))
            return Double.parseDouble(height.split("m")[0]);

        return differentSizeMap.entrySet().stream()
                .filter(e -> height.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
