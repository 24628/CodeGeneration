package io.swagger.api.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("Something went wrong authorizing you");
    }
}