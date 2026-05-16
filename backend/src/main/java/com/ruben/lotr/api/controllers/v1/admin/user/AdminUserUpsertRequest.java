package com.ruben.lotr.api.controllers.v1.admin.user;

public record AdminUserUpsertRequest(
                String name,
                String email,
                String password,
                String role) {
}