package com.ruben.lotr.core.auth.application.usecase;

public final class RegisterUserCommandMother {

    private RegisterUserCommandMother() {
    }

    public static RegisterUserCommand with(
            String name,
            String email,
            String password) {

        return new RegisterUserCommand(
                name != null ? name : "Frodo",
                email != null ? email : "frodo@mail.com",
                password != null ? password : "ring");
    }
}
