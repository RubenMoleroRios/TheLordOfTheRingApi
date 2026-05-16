package com.ruben.lotr.api.controllers.v1.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.usecase.RegisterUseCase;
import com.ruben.lotr.core.auth.application.usecase.RegisterUserCommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Endpoints publicos de autenticacion")
public class RegisterController {

    private final RegisterUseCase registerUseCase;

    public RegisterController(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario y devuelve su token JWT inicial.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuario registrado", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.AuthSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos no validos o usuario duplicado", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos necesarios para registrar un usuario", content = @Content(schema = @Schema(implementation = RegisterRequest.class), examples = @ExampleObject(name = "Registro demo", summary = "Payload listo para ejecutar", value = "{\n  \"name\": \"Samwise Gamgee\",\n  \"email\": \"samwise@lotr.local\",\n  \"password\": \"mellon123\"\n}")))
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody RegisterRequest request) {

        AuthResponse authResponse = registerUseCase.execute(
                new RegisterUserCommand(
                        request.name(),
                        request.email(),
                        request.password()));

        return ApiResponse.success(
                HttpStatusEnum.CREATED,
                AuthPresenter.present(authResponse),
                "User successfully registered.");
    }
}
