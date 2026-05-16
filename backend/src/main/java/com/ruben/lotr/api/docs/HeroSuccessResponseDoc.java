package com.ruben.lotr.api.docs;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HeroSuccessResponse", description = "Formato estandar de respuesta exitosa con un heroe")
public class HeroSuccessResponseDoc {

    @Schema(description = "Fecha y hora de la respuesta", example = "2026-03-12T18:40:12.123")
    private String timestamp;

    @Schema(description = "Estado logico de la respuesta", example = "success")
    private String status;

    @Schema(description = "Mensaje descriptivo", example = "Heroes successfully retrieved.")
    private String message;

    @Schema(description = "Heroe devuelto por el endpoint")
    private HeroResponseDTO data;

    @Schema(description = "Campo vacio cuando no hay error", example = "")
    private String error;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HeroResponseDTO getData() {
        return data;
    }

    public void setData(HeroResponseDTO data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}