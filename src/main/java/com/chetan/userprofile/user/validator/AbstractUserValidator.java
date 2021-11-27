package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.error.UserErrorCode;
import com.chetan.userprofile.user.service.UserService;
import com.chetan.userprofile.validator.ValidationUtils;
import com.chetan.userprofile.validator.Validator;

import java.util.Optional;

abstract class AbstractUserValidator implements Validator<UserDTO> {

    private final UserService userService;

    protected AbstractUserValidator(UserService userService) {
        this.userService = userService;
    }

    Optional<Error> validateFirstName(String firstName) {
        return ValidationUtils.validateIsEmpty(firstName, UserErrorCode.FIRST_NAME_REQUIRED);
    }

    Optional<Error> validatePassword(String password) {
        Optional<Error> requiredError =
                ValidationUtils.validateIsEmpty(password, UserErrorCode.PASSWORD_REQUIRED);

        if (requiredError.isEmpty()) {
            return requiredError;
        } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return Error.createOptionalError(UserErrorCode.INVALID_PASSWORD);
        } else {
            return Optional.empty();
        }
    }

    Optional<Error> validateEmail(String email, Long userId) {
        Optional<Error> requiredError =
                ValidationUtils.validateIsEmpty(email, UserErrorCode.EMAIL_REQUIRED);

        if (requiredError.isPresent()) {
            return requiredError;
        } else if (userService.isEmailUsed(email, userId)) {
            return Error.createOptionalError(UserErrorCode.EMAIL_IN_USE);
        } else {
            return Optional.empty();
        }
    }
}
