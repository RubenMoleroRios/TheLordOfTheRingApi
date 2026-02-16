package com.ruben.lotr.core.auth.domain.model;

import com.ruben.lotr.core.auth.domain.valueobject.*;

public final class UserMother {

    private UserMother() {
    }

    public static User create(
            UserNameVO name,
            UserEmailVO email,
            UserPasswordHashVO password) {
        return User.create(
                name != null ? name : UserNameVO.create("Frodo"),
                email != null ? email : UserEmailVO.create("frodo@mail.com"),
                password != null ? password : UserPasswordHashVO.fromHash("hashed-password12345"));
    }
}
