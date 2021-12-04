package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.exception.PlatformException;
import com.chetan.userprofile.exception.ValidationFailedException;

import java.util.List;

public interface MultiErrorsValidator<T> {

    List<ErrorCode> validateAndReturnErrors(T input);

    default void validate(T input) throws PlatformException {
        List<ErrorCode> errorCodes = validateAndReturnErrors(input);
        if (!errorCodes.isEmpty()) {
            throw new ValidationFailedException(errorCodes);
        }
    }
}
