package com.ruben.lotr.core.auth.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordHashVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;

public final class User {

    private final UserIdVO id;
    private final UserNameVO name;
    private final UserEmailVO email;
    private final UserPasswordHashVO password;

    private User(
            UserIdVO id,
            UserNameVO name,
            UserEmailVO email,
            UserPasswordHashVO password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static @NonNull User create(
            @NonNull UserNameVO name,
            @NonNull UserEmailVO email,
            @NonNull UserPasswordHashVO password) {

        return new User(
                UserIdVO.generate(),
                name,
                email,
                password);
    }

    public static @NonNull User fromPersistence(
            @NonNull UserIdVO id,
            @NonNull UserNameVO name,
            @NonNull UserEmailVO email,
            @NonNull UserPasswordHashVO password) {

        return new User(id, name, email, password);
    }

    public UserIdVO id() {
        return id;
    }

    public UserNameVO name() {
        return name;
    }

    public UserEmailVO email() {
        return email;
    }

    public UserPasswordHashVO password() {
        return password;
    }
}
