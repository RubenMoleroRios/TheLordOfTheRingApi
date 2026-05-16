package com.ruben.lotr.api.controllers.v1.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterRequest(
        @Schema(description = "Nombre visible del usuario", example = "Frodo Baggins") String name,
        @Schema(description = "Correo del usuario", example = "frodo@shire.me") String email,
        @Schema(description = "Contrasena del usuario", example = "ringBearer123") String password) {
}
