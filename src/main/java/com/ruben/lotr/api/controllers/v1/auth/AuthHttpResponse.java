package com.ruben.lotr.api.controllers.v1.auth;

public record AuthHttpResponse(
                String token,
                String userName) {
}