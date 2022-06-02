package io.swagger.responses.user;

import org.springframework.http.HttpStatus;

public class UserCreatedResponse {
    public final HttpStatus code;

    public UserCreatedResponse(HttpStatus code) {
        this.code = code;
    }

    public String toString() {
        return String.format("{code:%s,{Created successfully}}", code.value());
    }
}

