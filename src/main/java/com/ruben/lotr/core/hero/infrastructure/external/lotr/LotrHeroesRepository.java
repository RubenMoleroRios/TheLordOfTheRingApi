package com.ruben.lotr.core.hero.infrastructure.external.lotr;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.repository.HeroesRepositoryInterface;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.infrastructure.external.lotr.client.LotrApiResponseDTO;
import com.ruben.lotr.core.hero.infrastructure.external.lotr.client.LotrHeroResponseApiDTO;

@Repository
@Profile("api")
public class LotrHeroesRepository implements HeroesRepositoryInterface {

    private final WebClient webClient;
    private final HeroApiMapper mapper;

    @Value("${lotr.token}")
    private String apiToken;

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

    public LotrHeroesRepository(WebClient.Builder builder, HeroApiMapper mapper) {
        this.webClient = builder.baseUrl("https://the-one-api.dev/v2").build();
        this.mapper = mapper;
    }

    @Override
    public Optional<Hero> findById(HeroIdVO id) {

        String externalId = heroIdMap.get(id.value());
        if (externalId == null)
            return Optional.empty();

        List<LotrHeroResponseApiDTO> result = getHeroesFromApi("/" + externalId);
        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(mapper.toDomain(result.get(0), id, breedIdMap));
    }

    @Override
    public List<Hero> findAll() {
        return getHeroesFromApi("")
                .stream()
                .map(dto -> mapper.toDomain(dto, null, breedIdMap))
                .toList();
    }

    @Override
    public List<Hero> searchByBreedId(BreedIdVO breedId) {

        String race = breedIdMap.get(breedId.value());
        if (race == null)
            return List.of();

        return getHeroesFromApi("?race=" + race)
                .stream()
                .map(dto -> mapper.toDomain(dto, null, breedIdMap))
                .toList();
    }

    @Override
    public List<Hero> searchBySideId(SideIdVO sideId) {
        return List.of(); // no soportado por la API
    }

    private List<LotrHeroResponseApiDTO> getHeroesFromApi(String slug) {
        LotrApiResponseDTO response = webClient.get()
                .uri("/character" + slug)
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .bodyToMono(LotrApiResponseDTO.class)
                .block();

        return response != null && response.getDocs() != null
                ? response.getDocs()
                : List.of();
    }
}
