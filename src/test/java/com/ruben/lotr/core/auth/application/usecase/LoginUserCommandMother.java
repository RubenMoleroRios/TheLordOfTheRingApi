package com.ruben.lotr.core.auth.application.usecase;

public final class LoginUserCommandMother {

    private LoginUserCommandMother() {
    }

    public static LoginUserCommand with(
            String email,
            String password) {

        return new LoginUserCommand(
                email != null ? email : "frodo@mail.com",
                password != null ? password : "ring");
    }
}
