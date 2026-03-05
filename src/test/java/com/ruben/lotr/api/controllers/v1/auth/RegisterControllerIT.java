package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.application.usecase.RegisterUseCase;
import com.ruben.lotr.core.auth.application.usecase.RegisterUserCommand;
import com.ruben.lotr.core.auth.domain.exception.UserAlreadyExistsException;

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
class RegisterControllerIT {

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
        // ---------- ARRANGE (preparamos el escenario del test) ----------
        // Creamos el objeto que el caso de uso devolvería en un registro correcto.
        // (En este test NO se ejecuta el caso de uso real; lo mockeamos.)
        AuthResponse authResponse = new AuthResponse(
                "token-123",
                new UserResponse("user-1", "Ruben", "ruben@example.com"));

        // Configuramos el mock: cuando el controller llame a
        // registerUseCase.execute(...)
        // devolveremos el AuthResponse de arriba.
        when(registerUseCase.execute(any(RegisterUserCommand.class)))
                .thenReturn(authResponse);

        // ---------- ACT (ejecutamos la petición HTTP contra el controller) ----------
        // Con MockMvc simulamos una llamada HTTP real a nuestra API.
        mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Ruben","email":"ruben@example.com","password":"secret"}
                        """))
                // ---------- ASSERT (verificamos la respuesta HTTP y el JSON) ----------
                // 1) El endpoint debe responder 201 CREATED.
                .andExpect(status().isCreated())
                // 2) La respuesta debe ser JSON.
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // 3) Verificamos el wrapper ApiResponse (status, message, error, timestamp).
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("User successfully registered."))
                .andExpect(jsonPath("$.error").value(""))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                // 4) Verificamos el payload de datos (token y userName).
                .andExpect(jsonPath("$.data.token").value("token-123"))
                .andExpect(jsonPath("$.data.userName").value("Ruben"));

        // Capturamos el argumento con el que el controller llamó al caso de uso.
        // Esto nos permite comprobar que el controller construye bien el
        // RegisterUserCommand.
        ArgumentCaptor<RegisterUserCommand> captor = ArgumentCaptor.forClass(RegisterUserCommand.class);
        // Verificamos que el caso de uso fue invocado y guardamos el comando.
        verify(registerUseCase).execute(captor.capture());

        // Recuperamos el comando que realmente se pasó al caso de uso.
        RegisterUserCommand command = captor.getValue();
        // Comprobamos que el controller pasó name/email/password que venían en el JSON.
        assertEquals("Ruben", command.name());
        assertEquals("ruben@example.com", command.email());
        assertEquals("secret", command.password());
    }

    @Test
    void register_should_return_error_payload_when_user_already_exists() throws Exception {
        // ---------- ARRANGE ----------
        // Configuramos el mock para simular que el usuario ya existe.
        // El controller delega en el caso de uso; aquí forzamos que lance la excepción.
        when(registerUseCase.execute(any(RegisterUserCommand.class)))
                .thenThrow(new UserAlreadyExistsException("ruben@example.com"));

        // ---------- ACT ----------
        mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Ruben","email":"ruben@example.com","password":"secret"}
                        """))
                // ---------- ASSERT ----------
                // Según el mapeo actual de excepciones, UserAlreadyExistsException se traduce a
                // 404.
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Verificamos el wrapper ApiResponse de error.
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("User already exists with email: ruben@example.com"))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.code").value("UserAlreadyExistsException"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        // Verificamos que el controller sí intentó ejecutar el caso de uso.
        verify(registerUseCase).execute(any(RegisterUserCommand.class));
    }
}
