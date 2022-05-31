package io.swagger.api.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException {

    public InvalidUsernameOrPassword() {
        super("Invalid username or password");
    }
}