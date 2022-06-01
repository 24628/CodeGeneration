package io.swagger.responses;

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
    public String toString() {
        return String.format("{code:%s,{token:%s}}", code.value(), token);
    }
}
