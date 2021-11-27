package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.error.ErrorCode;

import java.util.Optional;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static Optional<Error> validateIsEmpty(String value, ErrorCode errorCode) {
        if (isEmpty(value)) {
            return Error.createOptionalError(errorCode);
        } else {
            return Optional.empty();
        }
    }
}
