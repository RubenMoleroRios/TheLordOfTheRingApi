package com.ruben.lotr.core.auth.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.auth.domain.valueobject.UserEmailVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserIdVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserPasswordVO;
import com.ruben.lotr.core.auth.domain.valueobject.UserNameVO;

public final class User {

    private final UserIdVO id;
    private final UserNameVO name;
    private final UserEmailVO email;
    private final UserPasswordVO password;

    private User(
            UserIdVO id,
            UserNameVO name,
            UserEmailVO email,
            UserPasswordVO password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /*
     * =========================
     * CASO 1: REGISTRO
     * =========================
     */
    public static @NonNull User create(
            @NonNull UserNameVO name,
            @NonNull UserEmailVO email,
            @NonNull UserPasswordVO password) {

        return new User(
                UserIdVO.generate(),
                name,
                email,
                password);
    }

    /*
     * =========================
     * CASO 2: RECONSTRUCCIÓN
     * =========================
     */
    public static @NonNull User fromPersistence(
            @NonNull UserIdVO id,
            @NonNull UserNameVO name,
            @NonNull UserEmailVO email,
            @NonNull UserPasswordVO password) {

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

    public UserPasswordVO password() {
        return password;
    }
}
