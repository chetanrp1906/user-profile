package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreateUserRequestValidator extends AbstractUserValidator {

    private static final long ZERO_USERID = 0L;

    public CreateUserRequestValidator(UserService userService) {
        super(userService);
    }

    @Override
    public Optional<List<Error>> validate(UserDTO userDTO) {
        Optional<Error> firstNameError = validateFirstName(userDTO.getFirstName());
        Optional<Error> passwordError = validatePassword(userDTO.getPassword());
        Optional<Error> emailError = validateEmail(userDTO.getEmail(), ZERO_USERID);
        return createErrorList(firstNameError, passwordError, emailError);
    }
}
