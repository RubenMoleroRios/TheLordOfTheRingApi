package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.infrastructure.persistence.SpringDataUserRepository;
import com.ruben.lotr.testsupport.MySqlTestContainerBase;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@SpringBootTest(classes = AuthFullStackIT.TestApplication.class, properties = {
                "spring.profiles.active=test,hibernate"
})
@AutoConfigureMockMvc(addFilters = false)
class AuthFullStackIT extends MySqlTestContainerBase {

        @Autowired
        private MockMvc mockMvc;

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
        void register_then_login_should_work_end_to_end_against_h2() throws Exception {
                // ARRANGE
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String password = "secret123";
                String name = "Ruben";

                // ACT
                mockMvc.perform(
                                post("/v1/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"" + name + "\",\"email\":\"" + email
                                                                + "\",\"password\":\"" + password
                                                                + "\"}"))
                                // ASSERT
                                .andExpect(status().isCreated())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("success"))
                                .andExpect(jsonPath("$.message").value("User successfully registered."))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                .andExpect(jsonPath("$.data.token", not(emptyOrNullString())))
                                .andExpect(jsonPath("$.data.userName").value(name));

                org.junit.jupiter.api.Assertions.assertTrue(
                                springDataUserRepository.findByEmail(email).isPresent(),
                                "Se esperaba que el usuario se persistiera después del registro");

                // ACT
                mockMvc.perform(
                                post("/v1/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"email\":\"" + email + "\",\"password\":\"" + password
                                                                + "\"}"))
                                // ASSERT
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("success"))
                                .andExpect(jsonPath("$.message").value("Welcome, " + name + "!"))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                .andExpect(jsonPath("$.data.token", not(emptyOrNullString())))
                                .andExpect(jsonPath("$.data.userName").value(name));
        }

        @Test
        void login_should_return_error_payload_when_password_is_wrong() throws Exception {
                // ARRANGE
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                String correctPassword = "secret123";
                String wrongPassword = "wrong";
                String name = "Ruben";

                // ACT
                mockMvc.perform(
                                post("/v1/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"" + name + "\",\"email\":\"" + email
                                                                + "\",\"password\":\""
                                                                + correctPassword + "\"}"))
                                .andExpect(status().isCreated());

                // ACT
                mockMvc.perform(
                                post("/v1/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"email\":\"" + email + "\",\"password\":\"" + wrongPassword
                                                                + "\"}"))
                                // ASSERT
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("error"))
                                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                                .andExpect(jsonPath("$.error").value("Not Found"))
                                .andExpect(jsonPath("$.code").value("InvalidCredentialsException"))
                                .andExpect(jsonPath("$.data").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty());
        }
}
