package io.swagger.api.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String name) {
        super(name + " does not exist");
    }
}
