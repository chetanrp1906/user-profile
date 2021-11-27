package com.chetan.userprofile.exception.handler;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class PlatformExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody List<Error> requestValidationFailedHandler(ValidationFailedException e) {
        return e.getErrors();
    }
}
