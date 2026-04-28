package com.ruben.lotr.core.auth.infrastructure.security;

import java.util.List;

public record AuthenticatedUser(
                String userId,
                String role,
                List<String> permissions) {
}