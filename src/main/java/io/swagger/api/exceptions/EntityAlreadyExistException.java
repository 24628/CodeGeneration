package io.swagger.api.exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String msg) {
        super(msg);
    }
}