package com.ruben.lotr.api.controllers.v1.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.auth.application.dto.AuthResponse;
import com.ruben.lotr.core.auth.application.usecase.LoginUseCase;
import com.ruben.lotr.core.auth.application.usecase.LoginUserCommand;

@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
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
