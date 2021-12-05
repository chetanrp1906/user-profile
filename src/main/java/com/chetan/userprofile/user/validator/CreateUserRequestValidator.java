package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.validator.MultiErrorsValidator;
import com.chetan.userprofile.validator.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreateUserRequestValidator implements MultiErrorsValidator<UserDTO> {

    private static final long ZERO_USERID = 0L;
    private final CommonUserRequestValidations commonUserRequestValidations;

    public CreateUserRequestValidator(CommonUserRequestValidations commonUserRequestValidations) {
        this.commonUserRequestValidations = commonUserRequestValidations;
    }

    @Override
    public List<ErrorCode> validateAndReturnErrors(UserDTO userDTO) {
        Optional<ErrorCode> firstNameError = commonUserRequestValidations.validateFirstName(userDTO.getFirstName());
        Optional<ErrorCode> passwordError = commonUserRequestValidations.validatePassword(userDTO.getPassword());
        Optional<ErrorCode> emailError = commonUserRequestValidations.validateEmail(userDTO.getEmail(), ZERO_USERID);
        return ValidationUtils
                .extractErrorCodes(List.of(firstNameError, passwordError, emailError));
    }
}
