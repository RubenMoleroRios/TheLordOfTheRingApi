package com.ruben.lotr.api.docs;

import java.util.List;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "HeroListSuccessResponse", description = "Formato estandar de respuesta exitosa con una lista de heroes")
public class HeroListSuccessResponseDoc {

    @Schema(description = "Fecha y hora de la respuesta", example = "2026-03-12T18:40:12.123")
    private String timestamp;

    @Schema(description = "Estado logico de la respuesta", example = "success")
    private String status;

    @Schema(description = "Mensaje descriptivo", example = "Heroes successfully retrieved.")
    private String message;

    @Schema(description = "Listado de heroes")
    private List<HeroResponseDTO> data;

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

    public List<HeroResponseDTO> getData() {
        return data;
    }

    public void setData(List<HeroResponseDTO> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}