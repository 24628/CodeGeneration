package io.swagger.responses.user;

import org.springframework.http.HttpStatus;

public class UserDeletedResponse {
    public final HttpStatus code;

    public UserDeletedResponse(HttpStatus code) {
        this.code = code;
    }

    public String toString() {
        return String.format("{code:%s,{User deleted successfully}}", code.value());
    }
}
