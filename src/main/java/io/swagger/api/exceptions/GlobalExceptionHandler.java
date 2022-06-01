package io.swagger.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// throws excption with message. with the illegal argument exception class
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseStatusException handleUserNotFound(UserNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public InvalidUsernameOrPassword handleInvalidUsernameOrPassword(InvalidUsernameOrPassword e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AuthorizationException.class)
    public AuthorizationException handleAuthorization(AuthorizationException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public EntityAlreadyExistException handleAuthorization(EntityAlreadyExistException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public ValidationException handleAuthorization(ValidationException e) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidPermissionsException.class)
    public InvalidPermissionsException handleAuthorization(InvalidPermissionsException e) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SerializeException.class)
    public SerializeException handleAuthorization(SerializeException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }



//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ResponseStatusException.class)
//    public void handleBadUsernameOrPassword() {
//        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password");
//    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResponseStatusException.class)
    public void handleAccessDenied() {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient rights");
    }
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ResponseStatusException.class)
//    public void handleInternalServerError(String message) {
//        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
//    }
}