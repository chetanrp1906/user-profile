package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.user.error.UserErrorCode;
import com.chetan.userprofile.user.service.UserService;
import com.chetan.userprofile.validator.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class CommonUserRequestValidations {

    private final UserService userService;

    public CommonUserRequestValidations(UserService userService) {
        this.userService = userService;
    }

    Optional<ErrorCode> validateFirstName(String firstName) {
        if (ValidationUtils.isEmpty(firstName)) {
            return Optional.of(UserErrorCode.FIRST_NAME_REQUIRED);
        } else {
            return Optional.empty();
        }
    }

    Optional<ErrorCode> validatePassword(String password) {
        if (ValidationUtils.isEmpty(password)) {
            return Optional.of(UserErrorCode.PASSWORD_REQUIRED);
        }

        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return Optional.of(UserErrorCode.INVALID_PASSWORD);
        }

        return Optional.empty();
    }

    Optional<ErrorCode> validateEmail(String email, Long userId) {
        if (ValidationUtils.isEmpty(email)) {
            return Optional.of(UserErrorCode.EMAIL_REQUIRED);
        }

        if (!email.matches(".+@.+\\..+")) {
            return Optional.of(UserErrorCode.INVALID_EMAIL);
        }

        if (userService.isEmailUsed(email, userId)) {
            return Optional.of(UserErrorCode.EMAIL_IN_USE);
        }

        return Optional.empty();
    }
}
