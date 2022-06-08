package io.swagger.responses.auth;

import io.swagger.model.Responses.UserLoginEntity;
import org.springframework.http.HttpStatus;

public class JwtTokenResponse {

    public final HttpStatus code;
    public final String token;
    public final UserLoginEntity userEntity;
    public JwtTokenResponse(HttpStatus code, String token, UserLoginEntity userEntity) {
        this.code = code;
        this.token = token;
        this.userEntity = userEntity;
    }
}