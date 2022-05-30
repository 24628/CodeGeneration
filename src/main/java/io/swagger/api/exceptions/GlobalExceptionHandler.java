package io.swagger.api.exceptions;

import io.swagger.model.Entity.ErrorEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

// throws excption with message. with the illegal argument exception class
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorEntity handleBadInput(IllegalArgumentException e) {
        return new ErrorEntity(e.getMessage(), "invalid username/password");
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