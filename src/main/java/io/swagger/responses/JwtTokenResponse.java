package io.swagger.responses;

import org.springframework.http.HttpStatus;

public class JwtTokenResponse {

    public final HttpStatus code;
    public final String token;
    public JwtTokenResponse(HttpStatus code, String token) {
        this.code = code;
        this.token = token;
    }

    public String toString() {
        return String.format("{code:%s,{token:%s}}", code.value(), token);
    }
}
