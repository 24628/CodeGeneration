package io.swagger.api.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super("username was not found " + name);
    }
}
