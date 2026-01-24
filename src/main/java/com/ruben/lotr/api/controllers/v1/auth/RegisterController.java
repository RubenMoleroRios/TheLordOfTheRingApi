package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.core.auth.application.usecase.RegisterUseCase;
import com.ruben.lotr.core.auth.application.usecase.RegisterUserCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class RegisterController {

    private final RegisterUseCase registerUseCase;

    public RegisterController(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request) {

        registerUseCase.execute(
                new RegisterUserCommand(
                        request.name(),
                        request.email(),
                        request.password()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
