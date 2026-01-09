package com.ruben.lotr.core.character.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ruben.lotr.core.character.domain.model.Breed;
import com.ruben.lotr.core.character.domain.model.Hero;
import com.ruben.lotr.core.character.domain.model.Side;
import com.ruben.lotr.core.character.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.character.domain.valueobject.BreedIdVO;
import com.ruben.lotr.core.character.domain.valueobject.BreedNameVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroDescriptionVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroEyesColorVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroHairColorVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroHeightVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroIdVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroLastNameVO;
import com.ruben.lotr.core.character.domain.valueobject.HeroNameVO;
import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;
import com.ruben.lotr.core.shared.util.NumberUtils;
import com.ruben.lotr.thelordofthering_api.dto.LortApiResponseDTO;
import com.ruben.lotr.thelordofthering_api.dto.LotrHeroApiDTO;

@Service
@Primary
public class ApiHeroesRepository implements HeroesRepositoryInterface {

    private final WebClient webClient;

    private final Map<String, String> breedIdMap = Map.of(
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440000").value(), "Human",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440001").value(), "Hobbit",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440002").value(), "Elf",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440003").value(), "Dwarf",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440004").value(), "Maiar",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440005").value(), "Ent",
            BreedIdVO.create("450e8400-e29b-41d4-a716-446655440006").value(), "Orc");

    private final Map<String, String> heroIdMap = Map.of(
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440001").value(), "5cd99d4bde30eff6ebccfd81",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440002").value(), "5cd99d4bde30eff6ebccfcc8",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440003").value(), "5cd99d4bde30eff6ebccfd06",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440004").value(), "5cd99d4bde30eff6ebccfbe6",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440005").value(), "5cd99d4bde30eff6ebccfc57",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440006").value(), "5cd99d4bde30eff6ebccfe19",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440007").value(), "5cd99d4bde30eff6ebccfd23",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440008").value(), "5cd99d4bde30eff6ebccfc15",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440009").value(), "5cd99d4bde30eff6ebccfd0d",
            HeroIdVO.create("550e8400-e29b-41d4-a716-446655440010").value(), "5cd99d4bde30eff6ebccfea0");

    private final Map<String, Double> differentSizeMap = Map.of(
            "Very tall", 3.0,
            "Tall", 2.0,
            "Tallest of the Elves of Gondolin", 2.5,
            "Short", 1.0);

    @Value("${lotr.token}")
    private String apiToken;

    private int heightCmToM = 100;

    public ApiHeroesRepository(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://the-one-api.dev/v2").build();
    }

    @Override
    public Optional<Hero> findById(HeroIdVO id) {

        String externalId = heroIdMap.get(id.value());
        if (externalId == null)
            return Optional.empty();

        List<LotrHeroApiDTO> result = getHeroesFromApi("/" + externalId);
        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(toDomain(result.get(0), id));
    }

    @Override
    public List<Hero> findAll() {
        return getHeroesFromApi("")
                .stream()
                .map(h -> toDomain(h, null))
                .toList();
    }

    @Override
    public List<Hero> searchByBreedId(BreedIdVO breedId) {

        String race = breedIdMap.get(breedId.value());
        if (race == null)
            return List.of();

        return getHeroesFromApi("?race=" + race)
                .stream()
                .map(h -> toDomain(h, null))
                .toList();
    }

    @Override
    public List<Hero> searchBySideId(SideIdVO sideId) {
        return List.of(); // no soportado por la API
    }

    /* ===================== MAPPING ===================== */

    private Hero toDomain(LotrHeroApiDTO h, HeroIdVO heroId) {

        String[] nameParts = h.getName().split(" ", 2);
        BreedNameVO breedName = BreedNameVO.create(h.getRace() != null ? h.getRace() : BreedNameVO.unknown().value());

        return Hero.create(
                heroId,
                Breed.create(BreedIdVO.generate(), breedName),
                Side.unknown(),
                new HeroNameVO(nameParts[0]),
                nameParts.length > 1 && !nameParts[1].isBlank() ? new HeroLastNameVO(nameParts[1])
                        : HeroLastNameVO.unknown(),
                HeroEyesColorVO.unknown(),
                h.getHair() != null && !h.getHair().isBlank() ? new HeroHairColorVO(h.getHair())
                        : HeroHairColorVO.unknown(),
                new HeroHeightVO(parseHeight(h.getHeight())),
                HeroDescriptionVO.unknown());
    }

    private List<LotrHeroApiDTO> getHeroesFromApi(String slug) {
        LortApiResponseDTO response = webClient.get()
                .uri("/character" + slug)
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .bodyToMono(LortApiResponseDTO.class)
                .block();
        if (response == null || response.getDocs() == null) {
            return List.of();
        }
        return response.getDocs();
    }

    private Double parseHeight(String height) {
        if (height == null || height.isEmpty()) {
            return null;
        }

        if (NumberUtils.isDouble(height.split("cm")[0])) {
            return this.parseHeightByHero(height, "cm") / heightCmToM;
        } else if (NumberUtils.isDouble(height.split("m")[0])) {
            return this.parseHeightByHero(height, "m");
        } else {
            Double size = this.getHeightByDifferentSize(height);
            return size > 0 ? size : null;
        }
    }

    private Double parseHeightByHero(String height, String regex) {
        String[] heigth = height.split(regex);
        return Double.valueOf(heigth[0].trim());
    }

    private Double getHeightByDifferentSize(String height) {
        for (String key : differentSizeMap.keySet()) {
            if (height.contains(key)) {
                return this.differentSizeMap.get(key);
            }
        }
        return 0.0;
    }
}