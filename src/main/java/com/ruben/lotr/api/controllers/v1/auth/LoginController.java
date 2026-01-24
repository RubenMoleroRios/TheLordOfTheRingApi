package com.ruben.lotr.api.controllers.v1.auth;

import com.ruben.lotr.core.auth.application.usecase.LoginUseCase;
import com.ruben.lotr.core.auth.application.usecase.LoginUserCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        String token = loginUseCase.execute(
                new LoginUserCommand(
                        request.email(),
                        request.password()));

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
