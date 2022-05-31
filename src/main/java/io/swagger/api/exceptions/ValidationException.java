package io.swagger.api.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }
}