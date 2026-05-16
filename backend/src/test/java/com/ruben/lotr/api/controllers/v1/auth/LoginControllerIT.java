package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.application.usecase.LoginUseCase;
import com.ruben.lotr.core.auth.application.usecase.LoginUserCommand;
import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;
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
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@SpringBootTest(classes = LoginControllerIT.TestApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerIT {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private LoginUseCase loginUseCase;

        @SpringBootConfiguration
        @EnableAutoConfiguration(exclude = {
                        DataSourceAutoConfiguration.class,
                        HibernateJpaAutoConfiguration.class,
                        FlywayAutoConfiguration.class,
                        SecurityAutoConfiguration.class,
                        SecurityFilterAutoConfiguration.class,
                        ManagementWebSecurityAutoConfiguration.class
        })
        @Import({
                        LoginController.class,
                        HandlerExceptionController.class
        })
        static class TestApplication {
        }

        @Test
        void login_should_return_200_and_success_payload() throws Exception {
                // ---------- ARRANGE ----------
                AuthResponse authResponse = new AuthResponse(
                                "token-abc",
                                new UserResponse("user-1", "Ruben", "ruben@example.com", "USER"));

                when(loginUseCase.execute(any(LoginUserCommand.class)))
                                .thenReturn(authResponse);

                // ---------- ACT ----------
                mockMvc.perform(post("/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"email":"ruben@example.com","password":"secret"}
                                                """))
                                // ---------- ASSERT ----------
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("success"))
                                .andExpect(jsonPath("$.message").value("Welcome, Ruben!"))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                .andExpect(jsonPath("$.data.token").value("token-abc"))
                                .andExpect(jsonPath("$.data.userName").value("Ruben"));

                ArgumentCaptor<LoginUserCommand> captor = ArgumentCaptor.forClass(LoginUserCommand.class);
                verify(loginUseCase).execute(captor.capture());

                LoginUserCommand command = captor.getValue();
                assertEquals("ruben@example.com", command.email());
                assertEquals("secret", command.password());
        }

        @Test
        void login_should_return_error_payload_when_credentials_are_invalid() throws Exception {
                // ---------- ARRANGE ----------
                when(loginUseCase.execute(any(LoginUserCommand.class)))
                                .thenThrow(new InvalidCredentialsException());

                // ---------- ACT ----------
                mockMvc.perform(post("/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {"email":"ruben@example.com","password":"wrong"}
                                                """))
                                // ---------- ASSERT ----------
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value("error"))
                                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                                .andExpect(jsonPath("$.error").value("Not Found"))
                                .andExpect(jsonPath("$.data").value(""))
                                .andExpect(jsonPath("$.code").value("InvalidCredentialsException"))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty());

                verify(loginUseCase).execute(any(LoginUserCommand.class));
        }
}
