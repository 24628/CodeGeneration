package io.swagger.responses.auth;

import io.swagger.model.Entity.UserEntity;
import org.springframework.http.HttpStatus;

public class JwtTokenResponse {

    public final HttpStatus code;
    public final String token;
    public final UserEntity userEntity;
    public JwtTokenResponse(HttpStatus code, String token, UserEntity userEntity) {
        this.code = code;
        this.token = token;
        this.userEntity = userEntity;
    }
}
