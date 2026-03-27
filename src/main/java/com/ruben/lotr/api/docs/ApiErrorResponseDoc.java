package com.ruben.lotr.api.docs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "Formato estandar de error devuelto por la API")
public class ApiErrorResponseDoc {

    @Schema(description = "Fecha y hora de la respuesta", example = "2026-03-12T18:40:12.123")
    private String timestamp;

    @Schema(description = "Estado logico de la respuesta", example = "error")
    private String status;

    @Schema(description = "Mensaje de negocio o error", example = "Invalid credentials")
    private String message;

    @Schema(description = "Datos devueltos en caso de error", example = "")
    private String data;

    @Schema(description = "Tipo HTTP del error", example = "Unauthorized")
    private String error;

    @Schema(description = "Codigo interno de la excepcion cuando existe", example = "InvalidCredentialsException")
    private String code;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}