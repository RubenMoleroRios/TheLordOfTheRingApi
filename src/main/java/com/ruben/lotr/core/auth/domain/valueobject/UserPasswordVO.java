package com.ruben.lotr.core.auth.domain.valueobject;

import com.ruben.lotr.core.shared.domain.valueobject.StringValueObject;

import java.util.Objects;

import org.springframework.lang.NonNull;

public final class UserPasswordVO extends StringValueObject {

    private UserPasswordVO(String value) {
        super(value);
    }

    /*
     * =========================
     * RECONSTRUCCIÓN DESDE DB
     * =========================
     * Se usa cuando el usuario ya existe
     * y el password viene hasheado
     */
    public static @NonNull UserPasswordVO fromHashed(String hashedPassword) {
        Objects.requireNonNull(hashedPassword, "Password hash cannot be null");
        return new UserPasswordVO(hashedPassword);
    }

    @Override
    protected boolean allowNull() {
        return false;
    }

    /**
     * ⚠️ IMPORTANTE
     * La longitud se valida sobre el HASH,
     * no sobre el texto plano.
     *
     * Un hash BCrypt real siempre supera
     * ampliamente esta longitud.
     */
    @Override
    protected int minLength() {
        return 20;
    }
}
