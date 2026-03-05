package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.api.controllers.HandlerExceptionController;
import com.ruben.lotr.core.auth.infrastructure.persistence.SpringDataUserRepository;

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

/**
 * Tests de integración full-stack del flujo de Auth.
 *
 * Estos tests levantan un contexto real de Spring (controllers + casos de uso +
 * repositorio JPA)
 * contra una base de datos H2 en memoria, y ejecutan peticiones HTTP con
 * MockMvc.
 *
 * Notas:
 * - Activamos el perfil "hibernate" porque los repositorios JPA reales están
 * anotados con @Profile("hibernate").
 * - Mantenemos el contexto de Spring lo más mínimo posible para evitar que
 * módulos no relacionados afecten al test.
 */
@Tag("integration")
@SpringBootTest(classes = AuthFullStackIT.TestApplication.class, properties = {
                // Sobrescribe el perfil Maven/sistema (test) y añade "hibernate" para que se
                // creen los repositorios JPA.
                "spring.profiles.active=test,hibernate"
})
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad para centrar el test en el flujo de auth.
class AuthFullStackIT {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private SpringDataUserRepository springDataUserRepository;

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = {
                        // Escanea solo el módulo de auth (casos de uso + beans de infraestructura como
                        // BCrypt/JWT/repositorio JPA).
                        "com.ruben.lotr.core.auth"
        })
        @EnableJpaRepositories(basePackages = {
                        // Habilita explícitamente el escaneo de repositorios Spring Data para el módulo
                        // de auth.
                        "com.ruben.lotr.core.auth.infrastructure.persistence"
        })
        @EntityScan(basePackages = {
                        // Escanea explícitamente las entidades JPA del módulo de auth.
                        "com.ruben.lotr.core.auth.infrastructure.persistence.entity"
        })
        @Import({
                        // Importa solo los controllers que queremos probar (evita traer otros
                        // controllers al contexto).
                        RegisterController.class,
                        LoginController.class,
                        // Importa el handler global de excepciones para que los errores se serialicen
                        // vía ApiResponse.
                        HandlerExceptionController.class
        })
        static class TestApplication {
                // Vacío: las anotaciones de arriba definen todo el contexto del test.
        }

        @Test
        void register_then_login_should_work_end_to_end_against_h2() throws Exception {
                // Arrange: generamos un email único para evitar colisiones entre ejecuciones.
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                // Arrange: elegimos una contraseña (>= 8 caracteres) que reutilizaremos en el
                // login.
                String password = "secret123";
                // Arrange: elegimos un nombre que debería reflejarse en las respuestas HTTP.
                String name = "Ruben";

                // Act + Assert (register): hacemos POST /v1/auth/register.
                mockMvc.perform(
                                // Construimos la petición HTTP al endpoint de registro.
                                post("/v1/auth/register")
                                                // Indicamos a Spring que enviamos JSON.
                                                .contentType(MediaType.APPLICATION_JSON)
                                                // Enviamos el body JSON que corresponde a
                                                // RegisterRequest(name,email,password).
                                                .content("{\"name\":\"" + name + "\",\"email\":\"" + email
                                                                + "\",\"password\":\"" + password
                                                                + "\"}"))
                                // Esperamos HTTP 201 CREATED si el registro va bien.
                                .andExpect(status().isCreated())
                                // Esperamos una respuesta JSON.
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                // Esperamos los campos del wrapper ApiResponse.
                                .andExpect(jsonPath("$.status").value("success"))
                                .andExpect(jsonPath("$.message").value("User successfully registered."))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                // Esperamos que el payload data (AuthHttpResponse) contenga token + userName.
                                .andExpect(jsonPath("$.data.token", not(emptyOrNullString())))
                                .andExpect(jsonPath("$.data.userName").value(name));

                // Assert (persistencia): el usuario ya debería existir en la base de datos H2.
                // Consultamos el repositorio Spring Data directamente para verificar que se
                // tocó la capa de persistencia.
                // (No comprobamos la entidad completa; con que exista nos vale.)
                org.junit.jupiter.api.Assertions.assertTrue(
                                springDataUserRepository.findByEmail(email).isPresent(),
                                "Se esperaba que el usuario se persistiera después del registro");

                // Act + Assert (login): hacemos POST /v1/auth/login con las mismas
                // credenciales.
                mockMvc.perform(
                                // Construimos la petición HTTP al endpoint de login.
                                post("/v1/auth/login")
                                                // Indicamos a Spring que enviamos JSON.
                                                .contentType(MediaType.APPLICATION_JSON)
                                                // Enviamos el body JSON que corresponde a LoginRequest(email,password).
                                                .content("{\"email\":\"" + email + "\",\"password\":\"" + password
                                                                + "\"}"))
                                // Esperamos HTTP 200 OK si el login va bien.
                                .andExpect(status().isOk())
                                // Esperamos una respuesta JSON.
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                // Esperamos los campos del wrapper ApiResponse.
                                .andExpect(jsonPath("$.status").value("success"))
                                // Esperamos que el mensaje de bienvenida incluya el userName devuelto por el
                                // presenter.
                                .andExpect(jsonPath("$.message").value("Welcome, " + name + "!"))
                                .andExpect(jsonPath("$.error").value(""))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                                // Esperamos que el payload data contenga un token + userName.
                                .andExpect(jsonPath("$.data.token", not(emptyOrNullString())))
                                .andExpect(jsonPath("$.data.userName").value(name));
        }

        @Test
        void login_should_return_error_payload_when_password_is_wrong() throws Exception {
                // Arrange: creamos un usuario único usando el endpoint real de register.
                String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                // Arrange: contraseña correcta usada en el registro (>= 8 caracteres).
                String correctPassword = "secret123";
                // Arrange: contraseña distinta para simular credenciales inválidas.
                String wrongPassword = "wrong";
                // Arrange: nombre (no se usa en el error de login, pero es obligatorio para
                // registrar).
                String name = "Ruben";

                // Act (register): persistimos el usuario pasando por el endpoint HTTP real.
                mockMvc.perform(
                                post("/v1/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"" + name + "\",\"email\":\"" + email
                                                                + "\",\"password\":\""
                                                                + correctPassword + "\"}"))
                                .andExpect(status().isCreated());

                // Act + Assert (login con password incorrecta): el caso de uso debería lanzar
                // InvalidCredentialsException,
                // y el HandlerExceptionController global debería serializarlo como un error
                // ApiResponse.
                mockMvc.perform(
                                post("/v1/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"email\":\"" + email + "\",\"password\":\"" + wrongPassword
                                                                + "\"}"))
                                // El mapeo actual devuelve 404 NOT_FOUND para InvalidCredentialsException.
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
