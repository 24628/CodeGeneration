package io.swagger.api.exceptions;

public class InvalidPermissionsException extends RuntimeException {

    public InvalidPermissionsException(String msg) {
        super(msg);
    }
}