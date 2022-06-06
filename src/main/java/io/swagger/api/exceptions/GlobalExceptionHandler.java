package io.swagger.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

// returns excption with message. with the illegal argument exception class
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ResponseStatusException handleIOException(IOException e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseStatusException handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseStatusException handleUserNotFound(UserNotFoundException e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseStatusException handleInvalidUsernameOrPassword(InvalidUsernameOrPassword e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AuthorizationException.class)
    public ResponseStatusException handleAuthorization(AuthorizationException e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseStatusException handleEntityAlreadyExist(EntityAlreadyExistException e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public ResponseStatusException handleValidation(ValidationException e) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidPermissionsException.class)
    public ResponseStatusException handleInvalidPermissions(InvalidPermissionsException e) {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SerializeException.class)
    public ResponseStatusException handleSerializeJson(SerializeException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseStatusException handleAccessDenied() {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient rights");
    }
}