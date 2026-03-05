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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test E2E (HTTP real) para {@link GetHeroesController}.
 *
 * Qué valida:
 * - Arranca servidor real (Tomcat) con puerto aleatorio.
 * - Hace HTTP real con {@link TestRestTemplate}.
 * - Respeta seguridad real: obtiene un JWT real llamando a /v1/auth/register y
 * lo manda en Authorization.
 * - Hace seed de datos en H2 (breed/side/heroes) y comprueba que la API
 * devuelve esos héroes.
 */
@Tag("integration")
@SpringBootTest(classes = GetHeroesE2EIT.TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.profiles.active=test,hibernate"
})
class GetHeroesE2EIT {

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
            // Incluimos core completo para traer auth + hero (use cases, repos, seguridad,
            // etc.).
            "com.ruben.lotr.core"
    })
    @EnableJpaRepositories(basePackages = {
            // Necesario para el repositorio Spring Data del módulo auth (registro/login
            // real).
            "com.ruben.lotr.core.auth.infrastructure.persistence"
    })
    @EntityScan(basePackages = {
            // Entidades JPA de auth.
            "com.ruben.lotr.core.auth.infrastructure.persistence.entity",
            // Entidades JPA de héroes (Hibernate repository).
            "com.ruben.lotr.core.hero.infrastructure.hibernate.entities"
    })
    @Import({
            // Controllers de auth, para conseguir JWT real.
            RegisterController.class,
            LoginController.class,
            // Controllers que probamos.
            GetHeroesController.class,
            // Handler global para el wrapper ApiResponse.
            HandlerExceptionController.class
    })
    static class TestApplication {
    }

    @Test
    void get_heroes_should_return_seeded_heroes_over_http_with_bearer_token() throws Exception {
        // ---------- ARRANGE ----------
        Seed seed = seedHeroes();

        String token = registerAndGetToken();

        // ---------- ACT ----------
        ResponseEntity<String> response = restTemplate.exchange(
                "/v1/heroes",
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
        assertTrue(json.path("timestamp").isTextual() && !json.path("timestamp").asText().isBlank());

        JsonNode data = json.path("data");
        assertTrue(data.isArray(), "Se esperaba que data fuese un array");
        assertTrue(data.size() >= 2, "Se esperaban al menos 2 héroes (los seed)");

        // Comprobamos que nuestros dos héroes seed están presentes por nombre.
        Set<String> names = new HashSet<>();
        for (JsonNode hero : data) {
            names.add(hero.path("name").asText());
        }
        assertTrue(names.contains(seed.legolasName), "Se esperaba encontrar a " + seed.legolasName);
        assertTrue(names.contains(seed.aragornName), "Se esperaba encontrar a " + seed.aragornName);
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
            // Creamos 2 breeds distintos.
            BreedEntity elf = instantiate(BreedEntity.class);
            elf.setId(UUID.randomUUID());
            elf.setName("Elf");

            BreedEntity human = instantiate(BreedEntity.class);
            human.setId(UUID.randomUUID());
            human.setName("Human");

            // Creamos un side.
            SideEntity good = instantiate(SideEntity.class);
            good.setId(UUID.randomUUID());
            good.setName("Good");

            entityManager.persist(elf);
            entityManager.persist(human);
            entityManager.persist(good);

            // Creamos 2 héroes con breeds diferentes.
            HeroEntity legolas = instantiate(HeroEntity.class);
            legolas.setId(UUID.randomUUID());
            legolas.setName("Legolas");
            legolas.setLastName("Greenleaf");
            legolas.setBreed(elf);
            legolas.setSide(good);
            legolas.setEyesColor("Blue");
            legolas.setHairColor("Blond");
            legolas.setHeight(1.85);
            legolas.setDescription("Prince of the Woodland Realm");

            HeroEntity aragorn = instantiate(HeroEntity.class);
            aragorn.setId(UUID.randomUUID());
            aragorn.setName("Aragorn");
            aragorn.setLastName("Elessar");
            aragorn.setBreed(human);
            aragorn.setSide(good);
            aragorn.setEyesColor("Gray");
            aragorn.setHairColor("Dark");
            aragorn.setHeight(1.98);
            aragorn.setDescription("Heir of Isildur");

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
