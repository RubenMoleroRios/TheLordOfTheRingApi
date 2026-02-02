package com.ruben.lotr.api.controllers.v1.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.usecase.RegisterUseCase;
import com.ruben.lotr.core.auth.application.usecase.RegisterUserCommand;

@RestController
@RequestMapping("/v1/auth")
public class RegisterController {

    private final RegisterUseCase registerUseCase;

    public RegisterController(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/register")
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
