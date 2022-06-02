package io.swagger.responses.user;

import org.springframework.http.HttpStatus;

public class UserDeletedResponse {
    public final HttpStatus code;

    public UserDeletedResponse(HttpStatus code) {
        this.code = code;
    }
}
