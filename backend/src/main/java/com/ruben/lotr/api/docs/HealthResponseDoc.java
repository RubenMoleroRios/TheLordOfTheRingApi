package com.ruben.lotr.api.docs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HealthResponse", description = "Respuesta simple del endpoint de health")
public class HealthResponseDoc {

    @Schema(description = "Estado de salud de la API", example = "ok")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}