package io.swagger.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

// throws excption with message. with the illegal argument exception class
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ResponseStatusException handleUserNotFound(IOException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseStatusException handleUserNotFound(EntityNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseStatusException handleUserNotFound(UserNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseStatusException handleInvalidUsernameOrPassword(InvalidUsernameOrPassword e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AuthorizationException.class)
    public ResponseStatusException handleAuthorization(AuthorizationException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseStatusException handleAuthorization(EntityAlreadyExistException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public ResponseStatusException handleAuthorization(ValidationException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidPermissionsException.class)
    public ResponseStatusException handleAuthorization(InvalidPermissionsException e) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SerializeException.class)
    public ResponseStatusException handleAuthorization(SerializeException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseStatusException handleAccessDenied() {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient rights");
    }
}