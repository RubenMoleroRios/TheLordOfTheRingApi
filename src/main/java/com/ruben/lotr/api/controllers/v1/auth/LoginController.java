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
import com.ruben.lotr.core.auth.application.usecase.LoginUseCase;
import com.ruben.lotr.core.auth.application.usecase.LoginUserCommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Endpoints publicos de autenticacion")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Autentica al usuario y devuelve un JWT junto al nombre del usuario.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login correcto", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.AuthSuccessResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Credenciales invalidas", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = com.ruben.lotr.api.docs.ApiErrorResponseDoc.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Credenciales del usuario. Puedes usar el usuario demo local para probar Swagger.", content = @Content(schema = @Schema(implementation = LoginRequest.class), examples = @ExampleObject(name = "Usuario demo", summary = "Credenciales listas para ejecutar", value = "{\n  \"email\": \"demo@lotr.local\",\n  \"password\": \"mellon123\"\n}")))
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest request) {

        AuthResponse authResponse = loginUseCase.execute(
                new LoginUserCommand(
                        request.email(),
                        request.password()));

        AuthHttpResponse response = AuthPresenter.present(authResponse);

        return ApiResponse.success(
                HttpStatusEnum.OK,
                response,
                "Welcome, " + response.userName() + "!");
    }
}
