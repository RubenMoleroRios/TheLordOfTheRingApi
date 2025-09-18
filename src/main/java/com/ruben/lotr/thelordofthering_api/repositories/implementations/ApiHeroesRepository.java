package com.ruben.lotr.thelordofthering_api.repositories.implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ruben.lotr.thelordofthering_api.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.dto.LortApiResponse;
import com.ruben.lotr.thelordofthering_api.dto.LotrHero;
import com.ruben.lotr.thelordofthering_api.repositories.interfaces.HeroesRepositoryInterface;

@Service
@Primary
public class ApiHeroesRepository implements HeroesRepositoryInterface {

    private final WebClient webClient;
    private Map<Long, String> breedIdMap;
    private Map<Long, String> heroIdMap;

    @Value("${lotr.token}")
    private String apiToken;

    public ApiHeroesRepository(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://the-one-api.dev/v2").build();

        this.breedIdMap = new HashMap<>();
        breedIdMap.put(1L, "Human");
        breedIdMap.put(2L, "Hobbit");
        breedIdMap.put(3L, "Elf");
        breedIdMap.put(4L, "Drawf");
        breedIdMap.put(5L, "Maiar");
        breedIdMap.put(6L, "Ent");
        breedIdMap.put(7L, "Orc");

        this.heroIdMap = new HashMap<>();
        heroIdMap.put(1L, "5cd99d4bde30eff6ebccfd81");
        heroIdMap.put(2L, "5cd99d4bde30eff6ebccfcc8");
        heroIdMap.put(3L, "5cd99d4bde30eff6ebccfd06");
        heroIdMap.put(4L, "5cd99d4bde30eff6ebccfbe6");
        heroIdMap.put(5L, "5cd99d4bde30eff6ebccfc57");
        heroIdMap.put(6L, "5cd99d4bde30eff6ebccfe19");
        heroIdMap.put(7L, "5cd99d4bde30eff6ebccfd23");
        heroIdMap.put(8L, "5cd99d4bde30eff6ebccfc15");
        heroIdMap.put(9L, "5cd99d4bde30eff6ebccfd0d");
        heroIdMap.put(10L, "5cd99d4bde30eff6ebccfea0");
    }

    private List<LotrHero> getHeroesFromApi(String slug) {

        LortApiResponse response = this.webClient.get()
                .uri("/character" + slug)
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .bodyToMono(LortApiResponse.class)
                .block();

        return response.getDocs();
    }

    @Override
    public List<HeroDTO> findAll() {
        List<LotrHero> externalHero = getHeroesFromApi("");

        return externalHero.stream().map(
                h -> {
                    String[] completeName = h.getName().split(" ", 2);
                    String name = completeName[0];
                    String lastName = completeName.length > 1 ? completeName[1] : "sin apellido";

                    Double heightHero = 0.0;

                    if (h.getHeight() != null && !h.getHeight().isEmpty()) {
                        String[] heigth = h.getHeight().split("cm");
                        try {
                            heightHero = Double.valueOf(heigth[0].trim());
                        } catch (NumberFormatException e) {
                            heightHero = 0.0;
                        }
                    }

                    return new HeroDTO(
                            null,
                            name,
                            lastName,
                            h.getRace(),
                            "Bando no definido",
                            "color de ojos no definido",
                            h.getHair(),
                            heightHero,
                            "Descripción no definida");
                }).collect(Collectors.toList());
    }

    @Override
    public HeroDTO findById(Long id) {

        List<LotrHero> externalHero = getHeroesFromApi("/" + this.heroIdMap.get(id));
        LotrHero hero = externalHero.get(0);

        String[] completeName = hero.getName().split(" ", 2);
        String name = completeName[0];
        String lastName = completeName.length > 1 ? completeName[1] : "sin apellido";

        Double heightHero = 0.0;

        if (hero.getHeight() != null && !hero.getHeight().isEmpty()) {
            String[] heigth = hero.getHeight().split("cm");
            try {
                heightHero = Double.valueOf(heigth[0].trim());
            } catch (NumberFormatException e) {
                heightHero = 0.0;
            }
        }

        return new HeroDTO(
                id,
                name,
                lastName,
                hero.getRace(),
                "Bando no definido",
                "Color de ojos no definido",
                hero.getHair(),
                heightHero,
                "Descripción no definida");
    }

    @Override
    public List<HeroDTO> searchByBreedId(Long breed) {

        List<LotrHero> externalHero = getHeroesFromApi("?race=" + this.breedIdMap.get(breed));

        return externalHero.stream()
                .map(
                        h -> {
                            String[] completeName = h.getName().split(" ", 2);
                            String name = completeName[0];
                            String lastName = completeName.length > 1 ? completeName[1] : "sin apellido";

                            Double heightHero = 0.0;

                            if (h.getHeight() != null && !h.getHeight().isEmpty()) {
                                String[] heigth = h.getHeight().split("cm");
                                try {
                                    heightHero = Double.valueOf(heigth[0].trim());
                                } catch (NumberFormatException e) {
                                    heightHero = 0.0;
                                }
                            }

                            return new HeroDTO(
                                    null,
                                    name,
                                    lastName,
                                    h.getRace(),
                                    "Bando no definido",
                                    "color de ojos no definido",
                                    h.getHair(),
                                    heightHero,
                                    "Descripción no definida");
                        })
                .collect(Collectors.toList());

    }

    @Override
    public List<HeroDTO> searchBySideId(Long side) {
        // TODO Auto-generated method stub
        return null;
    }

}
