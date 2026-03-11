package com.ruben.lotr.api.controllers.v1.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.infrastructure.persistence.SpringDataUserRepository;
import com.ruben.lotr.testsupport.MySqlTestContainerBase;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest(classes = AuthE2EIT.TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
                "spring.profiles.active=test,hibernate"
})
class AuthE2EIT extends MySqlTestContainerBase {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private SpringDataUserRepository springDataUserRepository;

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = {
                        "com.ruben.lotr.core.auth"
        })
        @EnableJpaRepositories(basePackages = {
                        "com.ruben.lotr.core.auth.infrastructure.persistence"
        })
        @EntityScan(basePackages = {
                        "com.ruben.lotr.core.auth.infrastructure.persistence.entity"
        })
        @Import({
                        RegisterController.class,
                        LoginController.class,
                        HandlerExceptionController.class
        })
        static class TestApplication {
        }

        @Test
        void register_then_login_should_work_over_real_http() throws Exception {
                // ---------- ARRANGE ----------
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String password = "secret123";
                String name = "Ruben";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> registerBody = new LinkedHashMap<>();
                registerBody.put("name", name);
                registerBody.put("email", email);
                registerBody.put("password", password);

                // ---------- ACT (REGISTER) ----------
                ResponseEntity<String> registerResponse = restTemplate.postForEntity(
                                "/v1/auth/register",
                                new HttpEntity<>(registerBody, headers),
                                String.class);

                // ---------- ASSERT (REGISTER) ----------
                assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
                assertNotNull(registerResponse.getBody());

                JsonNode registerJson = objectMapper.readTree(registerResponse.getBody());
                assertEquals("success", registerJson.path("status").asText());
                assertEquals("User successfully registered.", registerJson.path("message").asText());
                assertEquals("", registerJson.path("error").asText());
                assertTrue(registerJson.path("timestamp").isTextual()
                                && !registerJson.path("timestamp").asText().isBlank());
                assertTrue(registerJson.path("data").path("token").isTextual()
                                && !registerJson.path("data").path("token").asText().isBlank());
                assertEquals(name, registerJson.path("data").path("userName").asText());

                assertTrue(
                                springDataUserRepository.findByEmail(email).isPresent(),
                                "Se esperaba que el usuario se persistiera después del registro");

                // ---------- ACT (LOGIN) ----------
                Map<String, Object> loginBody = new LinkedHashMap<>();
                loginBody.put("email", email);
                loginBody.put("password", password);

                ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                                "/v1/auth/login",
                                new HttpEntity<>(loginBody, headers),
                                String.class);

                // ---------- ASSERT (LOGIN) ----------
                assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
                assertNotNull(loginResponse.getBody());

                JsonNode loginJson = objectMapper.readTree(loginResponse.getBody());
                assertEquals("success", loginJson.path("status").asText());
                assertEquals("Welcome, " + name + "!", loginJson.path("message").asText());
                assertEquals("", loginJson.path("error").asText());
                assertTrue(loginJson.path("timestamp").isTextual() && !loginJson.path("timestamp").asText().isBlank());
                assertTrue(loginJson.path("data").path("token").isTextual()
                                && !loginJson.path("data").path("token").asText().isBlank());
                assertEquals(name, loginJson.path("data").path("userName").asText());
        }

        @Test
        void login_should_return_error_payload_when_password_is_wrong_over_http() throws Exception {
                // ---------- ARRANGE ----------
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String correctPassword = "secret123";
                String wrongPassword = "wrong";
                String name = "Ruben";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> registerBody = new LinkedHashMap<>();
                registerBody.put("name", name);
                registerBody.put("email", email);
                registerBody.put("password", correctPassword);

                ResponseEntity<String> registerResponse = restTemplate.postForEntity(
                                "/v1/auth/register",
                                new HttpEntity<>(registerBody, headers),
                                String.class);
                assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());

                // ---------- ACT (LOGIN WRONG PASSWORD) ----------
                Map<String, Object> loginBody = new LinkedHashMap<>();
                loginBody.put("email", email);
                loginBody.put("password", wrongPassword);

                ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                                "/v1/auth/login",
                                new HttpEntity<>(loginBody, headers),
                                String.class);

                // ---------- ASSERT ----------
                assertEquals(HttpStatus.NOT_FOUND, loginResponse.getStatusCode());
                assertNotNull(loginResponse.getBody());

                JsonNode errorJson = objectMapper.readTree(loginResponse.getBody());
                assertEquals("error", errorJson.path("status").asText());
                assertEquals("Invalid credentials", errorJson.path("message").asText());
                assertEquals("Not Found", errorJson.path("error").asText());
                assertEquals("InvalidCredentialsException", errorJson.path("code").asText());
                assertTrue(errorJson.path("data").isTextual());
                assertEquals("", errorJson.path("data").asText());
                assertTrue(errorJson.path("timestamp").isTextual() && !errorJson.path("timestamp").asText().isBlank());
        }
}
