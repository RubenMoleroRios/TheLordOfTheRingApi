package com.ruben.lotr.core.auth.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.auth.domain.valueobject.*;

public final class UserMother {

    private UserMother() {
    }

    public static Builder aUser() {
        return new Builder();
    }

    public static final class Builder {
        private @NonNull UserIdVO id = UserIdVOMother.random();
        private @NonNull UserNameVO name = UserNameVOMother.frodo();
        private @NonNull UserEmailVO email = UserEmailVOMother.frodo();
        private @NonNull UserPasswordHashVO password = UserPasswordHashVOMother.defaultHash();

        public Builder withEmail(@NonNull UserEmailVO email) {
            this.email = email;
            return this;
        }

        public Builder withEmail(@NonNull String email) {
            return withEmail(UserEmailVO.create(email));
        }

        public User buildNew() {
            return User.create(name, email, password);
        }

        public User buildPersisted() {
            return User.fromPersistence(id, name, email, password);
        }

        // public Builder withPasswordHash(@NonNull UserPasswordHashVO password) {
        // this.password = password;
        // return this;

        // }
        // public Builder withName(@NonNull UserNameVO name) {
        // this.name = name;
        // return this;
        // }

        // public Builder withId(@NonNull UserIdVO id) {
        // this.id = id;
        // return this;
        // }
    }
}
