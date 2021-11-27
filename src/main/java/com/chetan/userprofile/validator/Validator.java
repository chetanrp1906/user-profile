package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.exception.ValidationFailedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Validator<T> {

    Optional<List<Error>> validate(T input);

    default void validateAndThrowException(T input) {
        Optional<List<Error>> errors = validate(input);
        if (errors.isPresent()) {
            throw new ValidationFailedException(errors.get());
        }
    }

    default Optional<List<Error>> createErrorList(Optional<Error>... errors) {
        List<Error> errorList = Arrays.stream(errors)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (errorList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(errorList);
        }
    }
}
