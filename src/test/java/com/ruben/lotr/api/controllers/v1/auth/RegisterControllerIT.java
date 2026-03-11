package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.application.usecase.RegisterUseCase;
import com.ruben.lotr.core.auth.application.usecase.RegisterUserCommand;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;
import com.ruben.lotr.testsupport.MySqlTestContainerBase;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@SpringBootTest(classes = RegisterControllerIT.TestApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class RegisterControllerIT extends MySqlTestContainerBase {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private RegisterUseCase registerUseCase;

        @SpringBootConfiguration
        @EnableAutoConfiguration(exclude = {
                        SecurityAutoConfiguration.class,
                        SecurityFilterAutoConfiguration.class,
                        ManagementWebSecurityAutoConfiguration.class
        })
        @Import({
                        RegisterController.class,
                        HandlerExceptionController.class
        })
        static class TestApplication {
        }

        @Test
        void register_should_return_201_and_success_payload() throws Exception {
                // ---------- ARRANGE ----------
                AuthResponse authResponse = new AuthResponse(
                                "token-123",
                                new UserResponse("user-1", "Ruben", "ruben@example.com"));

                when(registerUseCase.execute(any(RegisterUserCommand.class)))
                                .thenReturn(authResponse);

                // ---------- ACT ----------
                mockMvc.perform(post("/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"name":"Ruben","email":"ruben@example.com","password":"secret"}
                                                """))
                                // ---------- ASSERT ----------
                                .andExpect(status().isCreated())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("success"))
                                .andExpect(jsonPath("$.message").value("User successfully registered."))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                .andExpect(jsonPath("$.data.token").value("token-123"))
                                .andExpect(jsonPath("$.data.userName").value("Ruben"));

                ArgumentCaptor<RegisterUserCommand> captor = ArgumentCaptor.forClass(RegisterUserCommand.class);
                verify(registerUseCase).execute(captor.capture());

                RegisterUserCommand command = captor.getValue();
                assertEquals("Ruben", command.name());
                assertEquals("ruben@example.com", command.email());
                assertEquals("secret", command.password());
        }

        @Test
        void register_should_return_error_payload_when_user_already_exists() throws Exception {
                // ---------- ARRANGE ----------
                when(registerUseCase.execute(any(RegisterUserCommand.class)))
                                .thenThrow(new UserAlreadyExistsException("ruben@example.com"));

                // ---------- ACT ----------
                mockMvc.perform(post("/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"name":"Ruben","email":"ruben@example.com","password":"secret"}
                                                """))
                                // ---------- ASSERT ----------
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("error"))
                                .andExpect(jsonPath("$.message")
                                                .value("User already exists with email: ruben@example.com"))
                                .andExpect(jsonPath("$.error").value("Not Found"))
                                .andExpect(jsonPath("$.data").value(""))
                                .andExpect(jsonPath("$.code").value("UserAlreadyExistsException"))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty());

                verify(registerUseCase).execute(any(RegisterUserCommand.class));
        }
}
