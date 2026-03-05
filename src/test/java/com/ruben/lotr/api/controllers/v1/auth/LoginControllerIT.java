package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.application.usecase.LoginUseCase;
import com.ruben.lotr.core.auth.application.usecase.LoginUserCommand;
import com.ruben.lotr.core.auth.domain.exception.InvalidCredentialsException;

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
        // ---------- ARRANGE (preparamos el escenario del test) ----------
        // Creamos el objeto que el caso de uso devolvería en un login correcto.
        // (En estos tests NO ejecutamos el caso de uso real; lo mockeamos.)
        AuthResponse authResponse = new AuthResponse(
                "token-abc",
                new UserResponse("user-1", "Ruben", "ruben@example.com"));

        // Configuramos el mock: cuando el controller llame a loginUseCase.execute(...)
        // devolveremos el AuthResponse de arriba.
        when(loginUseCase.execute(any(LoginUserCommand.class)))
                .thenReturn(authResponse);

        // ---------- ACT (ejecutamos la petición HTTP contra el controller) ----------
        // Con MockMvc simulamos una llamada HTTP real a nuestra API.
        mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email":"ruben@example.com","password":"secret"}
                        """))
                // ---------- ASSERT (verificamos la respuesta HTTP y el JSON) ----------
                // 1) El endpoint debe responder 200 OK.
                .andExpect(status().isOk())
                // 2) La respuesta debe ser JSON.
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // 3) Verificamos el wrapper ApiResponse (status, message, error, timestamp).
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Welcome, Ruben!"))
                .andExpect(jsonPath("$.error").value(""))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                // 4) Verificamos el payload de datos (token y userName).
                .andExpect(jsonPath("$.data.token").value("token-abc"))
                .andExpect(jsonPath("$.data.userName").value("Ruben"));

        // Capturamos el argumento con el que el controller llamó al caso de uso.
        // Esto nos permite comprobar que el controller construye bien el
        // LoginUserCommand.
        ArgumentCaptor<LoginUserCommand> captor = ArgumentCaptor.forClass(LoginUserCommand.class);
        // Verificamos que el caso de uso fue invocado exactamente una vez y guardamos
        // el comando.
        verify(loginUseCase).execute(captor.capture());

        // Recuperamos el comando que realmente se pasó al caso de uso.
        LoginUserCommand command = captor.getValue();
        // Comprobamos que el controller pasó el email y el password que venían en el
        // JSON.
        assertEquals("ruben@example.com", command.email());
        assertEquals("secret", command.password());
    }

    @Test
    void login_should_return_error_payload_when_credentials_are_invalid() throws Exception {
        // ---------- ARRANGE ----------
        // Configuramos el mock para simular credenciales inválidas.
        // El controller delega en el caso de uso; aquí forzamos que lance la excepción.
        when(loginUseCase.execute(any(LoginUserCommand.class)))
                .thenThrow(new InvalidCredentialsException());

        // ---------- ACT ----------
        mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email":"ruben@example.com","password":"wrong"}
                        """))
                // ---------- ASSERT ----------
                // Según el mapeo actual de excepciones, InvalidCredentialsException se traduce
                // a 404.
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Verificamos el wrapper ApiResponse de error.
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.code").value("InvalidCredentialsException"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        // Verificamos que el controller sí intentó ejecutar el caso de uso.
        verify(loginUseCase).execute(any(LoginUserCommand.class));
    }
}
