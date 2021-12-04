package com.chetan.userprofile.exception;

import com.chetan.userprofile.error.ErrorCode;

import java.util.List;

public class ValidationFailedException extends PlatformException {

    private final List<ErrorCode> errorCodes;

    public ValidationFailedException(List<ErrorCode> errorCodes) {
        super("Validation Failed");
        this.errorCodes = errorCodes;
    }

    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
