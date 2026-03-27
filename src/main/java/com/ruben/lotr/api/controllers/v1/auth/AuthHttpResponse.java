package com.ruben.lotr.api.controllers.v1.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthHttpResponse(
        @Schema(description = "JWT de acceso", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcm9kb0BzaGlyZS5tZSJ9.signature") String token,
        @Schema(description = "Nombre del usuario autenticado", example = "Frodo Baggins") String userName) {
}