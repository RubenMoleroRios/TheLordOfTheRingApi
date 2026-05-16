package com.ruben.lotr.core.hero.infrastructure.external.lotr;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.ruben.lotr.core.hero.domain.model.Hero;
import com.ruben.lotr.core.hero.domain.valueobject.breed.BreedIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.hero.HeroIdVO;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class LotrHeroesRepositoryTest {

    private MockWebServer server;
    private LotrHeroesRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();

        String baseUrl = server.url("/").toString();
        repository = new LotrHeroesRepository(WebClient.builder(), new HeroApiMapper(), baseUrl);

        ReflectionTestUtils.setField(repository, "apiToken", "test-token");
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void findById_returnsEmpty_andDoesNotCallApi_whenHeroIdNotMapped() {
        HeroIdVO unmappedId = HeroIdVO.create("00000000-0000-0000-0000-000000000000");

        Optional<Hero> result = repository.findById(unmappedId);

        assertThat(result).isEmpty();
        assertThat(server.getRequestCount()).isZero();
    }

    @Test
    void findById_callsApi_andMapsHero_whenHeroIdMapped() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(
                        """
                                {"docs":[{"id":"5cd99d4bde30eff6ebccfd81","name":"Aragorn Elessar","race":"Human","hair":"Brown","height":"180cm"}]}
                                """));

        HeroIdVO mappedId = HeroIdVO.create("550e8400-e29b-41d4-a716-446655440001");

        Optional<Hero> result = repository.findById(mappedId);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(mappedId);
        assertThat(result.get().name().value()).isEqualTo("Aragorn");
        assertThat(result.get().lastName().value()).isEqualTo("Elessar");
        assertThat(result.get().breed().name().value()).isEqualTo("Human");

        var request = server.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/character/5cd99d4bde30eff6ebccfd81");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-token");
    }

    @Test
    void findAll_callsApi_andMapsHeroes() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {"docs":[
                          {"id":"1","name":"Frodo Baggins","race":"Hobbit","hair":"Brown","height":"1.06m"},
                          {"id":"2","name":"Gimli","race":"Dwarf","hair":"Red","height":"140cm"}
                        ]}
                        """));

        List<Hero> heroes = repository.findAll();

        assertThat(heroes).hasSize(2);
        assertThat(heroes.get(0).name().value()).isEqualTo("Frodo");
        assertThat(heroes.get(0).breed().name().value()).isEqualTo("Hobbit");

        var request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/character");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-token");
    }

    @Test
    void searchByBreedId_returnsEmpty_andDoesNotCallApi_whenBreedIdNotMapped() {
        BreedIdVO unmappedBreedId = BreedIdVO.create("00000000-0000-0000-0000-000000000000");

        List<Hero> heroes = repository.searchByBreedId(unmappedBreedId);

        assertThat(heroes).isEmpty();
        assertThat(server.getRequestCount()).isZero();
    }

    @Test
    void searchByBreedId_callsApi_withRaceQueryParam() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {"docs":[{"id":"1","name":"Aragorn","race":"Human","hair":"Brown","height":"180cm"}]}
                        """));

        BreedIdVO human = BreedIdVO.create("450e8400-e29b-41d4-a716-446655440000");

        List<Hero> heroes = repository.searchByBreedId(human);

        assertThat(heroes).hasSize(1);
        assertThat(heroes.get(0).breed().name().value()).isEqualTo("Human");

        var request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/character?race=Human");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-token");
    }

    @Test
    void searchBySideId_returnsEmptyList_becauseApiDoesNotSupportIt() {
        // sideId is ignored by this adapter (explicitly not supported)
        var sideId = com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO.create(
                "ce145f20-c5d3-42d2-89a1-1c9df74061f6");

        List<Hero> heroes = repository.searchBySideId(sideId);

        assertThat(heroes).isEmpty();
        assertThat(server.getRequestCount()).isZero();
    }
}
