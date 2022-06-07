package io.swagger.helpers;

import io.swagger.model.UserResponseEntity.UserLoginEntity;

public final class AuthResult {
    private final String token;
    private final UserLoginEntity user;

    public AuthResult(String token, UserLoginEntity user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserLoginEntity getUser() {
        return user;
    }
}
