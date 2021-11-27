package com.chetan.userprofile.exception;

import com.chetan.userprofile.error.Error;

import java.util.List;

public class ValidationFailedException extends PlatformException {

    private final List<Error> errors;

    public ValidationFailedException(List<Error> errors) {
        super("Validation Failed");
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
