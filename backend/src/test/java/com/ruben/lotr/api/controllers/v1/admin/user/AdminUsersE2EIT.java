package com.ruben.lotr.api.controllers.v1.admin.user;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.lotr.api.controllers.AuthErrorController;
import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.api.controllers.v1.auth.LoginController;
import com.ruben.lotr.api.controllers.v1.auth.LogoutController;
import com.ruben.lotr.api.controllers.v1.auth.RegisterController;
import com.ruben.lotr.core.auth.domain.model.Role;
import com.ruben.lotr.core.auth.domain.model.RoleName;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.domain.repository.RoleRepositoryInterface;
import com.ruben.lotr.core.auth.domain.repository.UserRepositoryInterface;
import com.ruben.lotr.core.auth.domain.service.PasswordHasher;
import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;
import com.ruben.lotr.testsupport.MySqlTestContainerBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
@SpringBootTest(classes = AdminUsersE2EIT.TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.profiles.active=test,hibernate"
})
class AdminUsersE2EIT extends MySqlTestContainerBase {

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

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = {
                        "com.ruben.lotr.core.auth"
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
                        AdminUsersController.class,
                        AuthErrorController.class,
                        HandlerExceptionController.class
        })
        static class TestApplication {
        }

        @Test
        void admin_should_manage_users_end_to_end() throws Exception {
                String adminToken = createAdminAndLogin();

                Map<String, Object> createBody = new LinkedHashMap<>();
                createBody.put("name", "Arwen");
                createBody.put("email", "arwen_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
                createBody.put("password", "mellon123");
                createBody.put("role", "ADMIN");

                ResponseEntity<String> createResponse = restTemplate.exchange(
                                "/v1/admin/users",
                                HttpMethod.POST,
                                new HttpEntity<>(createBody, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
                JsonNode createJson = objectMapper.readTree(createResponse.getBody());
                assertEquals("ADMIN", createJson.path("data").path("role").asText());
                String createdUserId = createJson.path("data").path("id").asText();

                ResponseEntity<String> listResponse = restTemplate.exchange(
                                "/v1/admin/users",
                                HttpMethod.GET,
                                new HttpEntity<>(null, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, listResponse.getStatusCode());
                JsonNode listJson = objectMapper.readTree(listResponse.getBody());
                assertTrue(listJson.path("data").isArray());
                assertTrue(listJson.path("data").toString().contains(createBody.get("email").toString()));

                Map<String, Object> updateBody = new LinkedHashMap<>();
                updateBody.put("name", "Arwen Evenstar");
                updateBody.put("email", createBody.get("email"));
                updateBody.put("password", "");
                updateBody.put("role", "USER");

                ResponseEntity<String> updateResponse = restTemplate.exchange(
                                "/v1/admin/users/" + createdUserId,
                                HttpMethod.PUT,
                                new HttpEntity<>(updateBody, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
                JsonNode updateJson = objectMapper.readTree(updateResponse.getBody());
                assertEquals("USER", updateJson.path("data").path("role").asText());
                assertEquals("Arwen Evenstar", updateJson.path("data").path("name").asText());

                ResponseEntity<String> deleteResponse = restTemplate.exchange(
                                "/v1/admin/users/" + createdUserId,
                                HttpMethod.DELETE,
                                new HttpEntity<>(null, authHeaders(adminToken)),
                                String.class);

                assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
                assertFalse(userRepository
                                .findById(com.ruben.lotr.core.auth.domain.valueobject.UserIdVO.create(createdUserId))
                                .isPresent());
        }

        @Test
        void normal_user_should_not_access_admin_user_endpoints() throws Exception {
                String userToken = registerAndGetToken();

                Map<String, Object> createBody = new LinkedHashMap<>();
                createBody.put("name", "Blocked User");
                createBody.put("email", "blocked_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com");
                createBody.put("password", "secret123");
                createBody.put("role", "USER");

                ResponseEntity<String> response = restTemplate.exchange(
                                "/v1/admin/users",
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
                                UserNameVO.create("Admin User"),
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
                body.put("name", "Normal User");
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
}