package com.ruben.lotr.api.controllers.v1.admin.hero;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.lotr.api.controllers.AuthErrorController;
import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.api.controllers.v1.auth.LoginController;
import com.ruben.lotr.api.controllers.v1.auth.LogoutController;
import com.ruben.lotr.api.controllers.v1.auth.RegisterController;
import com.ruben.lotr.api.controllers.v1.hero.GetHeroesController;
import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.BreedEntity;
import com.ruben.lotr.core.hero.infrastructure.hibernate.entities.SideEntity;
import com.ruben.lotr.testsupport.MySqlTestContainerBase;

import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
@SpringBootTest(classes = AdminHeroesE2EIT.TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.profiles.active=test,hibernate"
})
class AdminHeroesE2EIT extends MySqlTestContainerBase {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepositoryInterface userRepository;

        @Autowired
        private RoleRepositoryInterface roleRepository;

        @Autowired
        private PasswordHasher passwordHasher;

        @Autowired
        private EntityManager entityManager;

        @Autowired
        private PlatformTransactionManager transactionManager;

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = {
                        "com.ruben.lotr.core.auth",
                        "com.ruben.lotr.core.hero.application.usecase"
        })
        @EnableJpaRepositories(basePackages = {
                        "com.ruben.lotr.core.auth.infrastructure.persistence"
        })
        @EntityScan(basePackages = {
                        "com.ruben.lotr.core.auth.infrastructure.persistence.entity",
                        "com.ruben.lotr.core.hero.infrastructure.hibernate.entities"
        })
        @Import({
                        LoginController.class,
                        RegisterController.class,
                        LogoutController.class,
                        GetHeroesController.class,
                        AdminHeroesController.class,
                        com.ruben.lotr.core.hero.infrastructure.hibernate.HibernateHeroesRepository.class,
                        com.ruben.lotr.core.hero.infrastructure.hibernate.HeroEntityMapper.class,
                        AuthErrorController.class,
                        HandlerExceptionController.class
        })
        static class TestApplication {
        }

        @Test
        void admin_should_create_update_and_delete_hero() throws Exception {
                String adminToken = createAdminAndLogin();
                Seed seed = seedCatalog();

                Map<String, Object> createBody = new LinkedHashMap<>();
                createBody.put("name", "Eowyn");
                createBody.put("lastName", "of Rohan");
                createBody.put("eyesColor", "Blue");
                createBody.put("hairColor", "Blonde");
                createBody.put("height", 1.70);
                createBody.put("description", "Shieldmaiden of Rohan");
                createBody.put("breedId", seed.breedId());
                createBody.put("sideId", seed.sideId());

                ResponseEntity<String> createResponse = restTemplate.exchange(
                                "/v1/admin/heroes",
                                HttpMethod.POST,
                                new HttpEntity<>(createBody, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
                JsonNode createJson = objectMapper.readTree(createResponse.getBody());
                String heroId = createJson.path("data").path("id").asText();
                assertEquals("Eowyn", createJson.path("data").path("name").asText());

                Map<String, Object> updateBody = new LinkedHashMap<>(createBody);
                updateBody.put("description", "Slayer of the Witch-king");

                ResponseEntity<String> updateResponse = restTemplate.exchange(
                                "/v1/admin/heroes/" + heroId,
                                HttpMethod.PUT,
                                new HttpEntity<>(updateBody, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
                JsonNode updateJson = objectMapper.readTree(updateResponse.getBody());
                assertEquals("Slayer of the Witch-king", updateJson.path("data").path("description").asText());

                ResponseEntity<String> deleteResponse = restTemplate.exchange(
                                "/v1/admin/heroes/" + heroId,
                                HttpMethod.DELETE,
                                new HttpEntity<>(null, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

                ResponseEntity<String> listResponse = restTemplate.exchange(
                                "/v1/heroes",
                                HttpMethod.GET,
                                new HttpEntity<>(null, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, listResponse.getStatusCode());
                assertFalse(listResponse.getBody().contains(heroId));
        }

        @Test
        void normal_user_should_not_access_admin_hero_endpoints() throws Exception {
                String userToken = registerAndGetToken();
                Seed seed = seedCatalog();

                Map<String, Object> createBody = new LinkedHashMap<>();
                createBody.put("name", "Blocked Hero");
                createBody.put("lastName", "Test");
                createBody.put("eyesColor", "Brown");
                createBody.put("hairColor", "Brown");
                createBody.put("height", 1.60);
                createBody.put("description", "Should not be created");
                createBody.put("breedId", seed.breedId());
                createBody.put("sideId", seed.sideId());

                ResponseEntity<String> response = restTemplate.exchange(
                                "/v1/admin/heroes",
                                HttpMethod.POST,
                                new HttpEntity<>(createBody, authHeaders(userToken)),
                                String.class);

                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        private String createAdminAndLogin() throws Exception {
                String email = "admin_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String password = "secret123";

                Role adminRole = roleRepository.findByName(RoleName.ADMIN).orElseThrow();
                User adminUser = User.create(
                                UserNameVO.create("Hero Admin"),
                                UserEmailVO.create(email),
                                UserPasswordHashVO.fromHash(passwordHasher.hash(password)),
                                adminRole);
                userRepository.save(adminUser);

                return loginAndGetToken(email, password);
        }

        private String registerAndGetToken() throws Exception {
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String password = "secret123";

                Map<String, Object> body = new LinkedHashMap<>();
                body.put("name", "Hero User");
                body.put("email", email);
                body.put("password", password);

                ResponseEntity<String> response = restTemplate.postForEntity(
                                "/v1/auth/register",
                                new HttpEntity<>(body, jsonHeaders()),
                                String.class);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                return objectMapper.readTree(response.getBody()).path("data").path("token").asText();
        }

        private String loginAndGetToken(String email, String password) throws Exception {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("email", email);
                body.put("password", password);

                ResponseEntity<String> response = restTemplate.postForEntity(
                                "/v1/auth/login",
                                new HttpEntity<>(body, jsonHeaders()),
                                String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                return objectMapper.readTree(response.getBody()).path("data").path("token").asText();
        }

        private Seed seedCatalog() {
                TransactionTemplate transaction = new TransactionTemplate(transactionManager);
                return transaction.execute(status -> {
                        BreedEntity breed = new BreedEntity(UUID.randomUUID(), "Human");
                        SideEntity side = new SideEntity(UUID.randomUUID(), "Good");

                        entityManager.persist(breed);
                        entityManager.persist(side);
                        entityManager.flush();

                        return new Seed(breed.getId().toString(), side.getId().toString());
                });
        }

        private HttpHeaders authHeaders(String token) {
                HttpHeaders headers = jsonHeaders();
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(token);
                return headers;
        }

        private HttpHeaders jsonHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
        }

        private record Seed(String breedId, String sideId) {
        }
}