package com.ruben.lotr.api.controllers.v1.admin.user;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.auth.application.dto.UserResponse;
import com.ruben.lotr.core.auth.application.usecase.AdminCreateUserCommand;
import com.ruben.lotr.core.auth.application.usecase.AdminCreateUserUseCase;
import com.ruben.lotr.core.auth.application.usecase.AdminUpdateUserCommand;
import com.ruben.lotr.core.auth.application.usecase.AdminUpdateUserUseCase;
import com.ruben.lotr.core.auth.application.usecase.DeleteUserUseCase;
import com.ruben.lotr.core.auth.application.usecase.GetUserByIdUseCase;
import com.ruben.lotr.core.auth.application.usecase.GetUsersUseCase;
import com.ruben.lotr.core.auth.domain.model.User;
import com.ruben.lotr.core.auth.infrastructure.security.AuthenticatedUser;

@RestController
@RequestMapping("/v1/admin/users")
public class AdminUsersController {

    private final AdminCreateUserUseCase adminCreateUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final AdminUpdateUserUseCase adminUpdateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public AdminUsersController(
            AdminCreateUserUseCase adminCreateUserUseCase,
            GetUsersUseCase getUsersUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            AdminUpdateUserUseCase adminUpdateUserUseCase,
            DeleteUserUseCase deleteUserUseCase) {
        this.adminCreateUserUseCase = adminCreateUserUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.adminUpdateUserUseCase = adminUpdateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody AdminUserUpsertRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser) {

        User createdUser = adminCreateUserUseCase.execute(
                new AdminCreateUserCommand(
                        request.name(),
                        request.email(),
                        request.password(),
                        request.role(),
                        currentUser.role()));

        return ApiResponse.success(HttpStatusEnum.CREATED, toResponse(createdUser), "User successfully created.");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<UserResponse> users = getUsersUseCase.execute()
                .stream()
                .map(this::toResponse)
                .toList();

        return ApiResponse.success(HttpStatusEnum.OK, users, "Users successfully retrieved.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable String id) {
        return ApiResponse.success(
                HttpStatusEnum.OK,
                toResponse(getUserByIdUseCase.execute(id)),
                "User successfully retrieved.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @RequestBody AdminUserUpsertRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser) {

        User updatedUser = adminUpdateUserUseCase.execute(
                new AdminUpdateUserCommand(
                        id,
                        request.name(),
                        request.email(),
                        request.password(),
                        request.role(),
                        currentUser.role()));

        return ApiResponse.success(HttpStatusEnum.OK, toResponse(updatedUser), "User successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        deleteUserUseCase.execute(id);
        return ApiResponse.success(HttpStatusEnum.OK, "", "User successfully deleted.");
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.id().value(),
                user.name().value(),
                user.email().value(),
                user.role().name());
    }
}