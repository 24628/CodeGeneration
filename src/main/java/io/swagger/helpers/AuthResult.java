package io.swagger.helpers;

import io.swagger.model.Entity.UserEntity;

public final class AuthResult {
    private final String token;
    private final UserEntity user;

    public AuthResult(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserEntity getUser() {
        return user;
    }
}
