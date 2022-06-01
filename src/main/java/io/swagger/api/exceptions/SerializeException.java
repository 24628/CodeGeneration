package io.swagger.api.exceptions;

public class SerializeException extends RuntimeException {

    public SerializeException() {
        super("Couldn't serialize response for content type application/json");
    }
}
