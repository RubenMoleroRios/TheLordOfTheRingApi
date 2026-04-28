package com.ruben.lotr.api.controllers.v1.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.auth.infrastructure.security.AuthenticatedUser;

@RestController
@RequestMapping("/v1/auth")
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@AuthenticationPrincipal AuthenticatedUser currentUser) {
        // En un esquema stateless no se destruye sesión en servidor: el cliente deja de
        // usar el JWT.
        return ApiResponse.success(
                HttpStatusEnum.OK,
                Map.of("userId", currentUser.userId()),
                "Logout completed. Remove the token from the client to finish the session.");
    }
}