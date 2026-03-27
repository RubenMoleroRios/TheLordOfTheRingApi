package com.ruben.lotr.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("ApiTLOR")
                                                .version("v1")
                                                .description(
                                                                "Documentacion interactiva de la API de The Lord of the Rings. Incluye endpoints publicos de autenticacion y endpoints protegidos para consultar heroes.\n\nUsuario demo para pruebas locales con Swagger UI:\n- email: `demo@lotr.local`\n- password: `mellon123`\n\nFlujo recomendado:\n1. Ejecuta `POST /v1/auth/login` con el ejemplo incluido.\n2. Copia el valor de `data.token` de la respuesta.\n3. Pulsa `Authorize` y pega el JWT sin el prefijo `Bearer`.\n4. Prueba los endpoints protegidos de heroes."))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .description(
                                                                                "Introduce el token JWT obtenido en /v1/auth/login o /v1/auth/register.")));
        }
}