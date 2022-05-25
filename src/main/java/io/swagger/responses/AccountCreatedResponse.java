package io.swagger.responses;

import org.springframework.http.HttpStatus;

public class AccountCreatedResponse {

    public final HttpStatus code;

    public AccountCreatedResponse(HttpStatus code) {
        this.code = code;
    }

    public String toString() {
        return String.format("{code:%s,{Created successfully}}", code.value());
    }
}
