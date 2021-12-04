package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.ErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static List<ErrorCode> extractErrorCodes(List<Optional<ErrorCode>> optionalErrorCodes) {
        return optionalErrorCodes
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
