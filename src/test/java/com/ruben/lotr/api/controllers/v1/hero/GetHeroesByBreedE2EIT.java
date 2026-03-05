package com.ruben.lotr.api.controllers.v1.hero;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.api.controllers.v1.auth.LoginController;
import com.ruben.lotr.api.controllers.v1.auth.RegisterController;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.BreedEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.HeroEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.SideEntity;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test E2E (HTTP real) para {@link GetHeroesByBreedController}.
 *
 * Qué valida:
 * - Endpoint protegido: usa JWT real (Authorization: Bearer ...).
 * - Seed de datos en H2 con 2 breeds distintos.
 * - Al pedir un breedId concreto, devuelve SOLO héroes de ese breed.
 */
@Tag("integration")
@SpringBootTest(classes = GetHeroesByBreedE2EIT.TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=test,hibernate"
})
class GetHeroesByBreedE2EIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = {
            "com.ruben.lotr.core"
    })
    @EnableJpaRepositories(basePackages = {
            "com.ruben.lotr.core.auth.infrastructure.persistence"
    })
    @EntityScan(basePackages = {
            "com.ruben.lotr.core.auth.infrastructure.persistence.entity",
            "com.ruben.lotr.core.hero.infrastructure.hibernate.entities"
    })
    @Import({
            RegisterController.class,
            LoginController.class,
            GetHeroesByBreedController.class,
            HandlerExceptionController.class
    })
    static class TestApplication {
    }

    @Test
    void get_heroes_by_breed_should_return_only_that_breed() throws Exception {
        // ---------- ARRANGE ----------
        Seed seed = seedHeroes();
        String token = registerAndGetToken();

        String encodedBreedId = URLEncoder.encode(seed.elfBreedId, StandardCharsets.UTF_8);

        // ---------- ACT ----------
        ResponseEntity<String> response = restTemplate.exchange(
                "/v1/breeds/" + encodedBreedId + "/heroes",
                HttpMethod.GET,
                new HttpEntity<>(null, authHeaders(token)),
                String.class);

        // ---------- ASSERT ----------
        if (response.getStatusCode() != HttpStatus.OK) {
            fail("Se esperaba 200 OK pero fue " + response.getStatusCode() + ". Body: " + response.getBody());
        }
        assertNotNull(response.getBody());

        JsonNode json = objectMapper.readTree(response.getBody());
        assertEquals("success", json.path("status").asText());
        assertEquals("Heroes successfully retrieved.", json.path("message").asText());
        assertEquals("", json.path("error").asText());

        JsonNode data = json.path("data");
        assertTrue(data.isArray(), "Se esperaba que data fuese un array");
        assertTrue(data.size() >= 1, "Se esperaba al menos 1 héroe para el breed seed");

        // Todos los resultados deben venir con breedName = Elf.
        for (JsonNode hero : data) {
            assertEquals(seed.elfBreedName, hero.path("breedName").asText());
        }

        // Y debe contener a Legolas, pero NO a Aragorn (porque es Human).
        Set<String> names = new HashSet<>();
        for (JsonNode hero : data) {
            names.add(hero.path("name").asText());
        }
        assertTrue(names.contains(seed.legolasName));
        assertFalse(names.contains(seed.aragornName));
    }

    private HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private String registerAndGetToken() throws Exception {
        String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        String password = "secret123"; // >= 8
        String name = "Ruben";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", name);
        body.put("email", email);
        body.put("password", password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/v1/auth/register",
                new HttpEntity<>(body, headers),
                String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        JsonNode json = objectMapper.readTree(response.getBody());
        String token = json.path("data").path("token").asText();
        assertNotNull(token);
        assertFalse(token.isBlank());
        return token;
    }

    private Seed seedHeroes() {
        TransactionTemplate tx = new TransactionTemplate(transactionManager);
        return tx.execute(status -> {
            BreedEntity elf = instantiate(BreedEntity.class);
            elf.setId(UUID.randomUUID());
            elf.setName("Elf");

            BreedEntity human = instantiate(BreedEntity.class);
            human.setId(UUID.randomUUID());
            human.setName("Human");

            SideEntity good = instantiate(SideEntity.class);
            good.setId(UUID.randomUUID());
            good.setName("Good");

            entityManager.persist(elf);
            entityManager.persist(human);
            entityManager.persist(good);

            HeroEntity legolas = instantiate(HeroEntity.class);
            legolas.setId(UUID.randomUUID());
            legolas.setName("Legolas");
            legolas.setLastName("Greenleaf");
            legolas.setBreed(elf);
            legolas.setSide(good);
            // Campos que en dominio NO aceptan null (si quieres "vacío", usa "").
            legolas.setHairColor("Blond");
            legolas.setDescription("");

            HeroEntity aragorn = instantiate(HeroEntity.class);
            aragorn.setId(UUID.randomUUID());
            aragorn.setName("Aragorn");
            aragorn.setLastName("Elessar");
            aragorn.setBreed(human);
            aragorn.setSide(good);
            aragorn.setHairColor("Dark");
            aragorn.setDescription("");

            entityManager.persist(legolas);
            entityManager.persist(aragorn);
            entityManager.flush();

            return new Seed(
                    elf.getId().toString(),
                    elf.getName(),
                    legolas.getName(),
                    aragorn.getName());
        });
    }

    private static <T> T instantiate(Class<T> type) {
        try {
            var ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo instanciar " + type.getName() + " vía reflexión", e);
        }
    }

    private record Seed(
            String elfBreedId,
            String elfBreedName,
            String legolasName,
            String aragornName) {
    }
}
