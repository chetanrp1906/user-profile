package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.user.error.UserErrorCode;
import com.chetan.userprofile.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonUserRequestValidationsTests {

    @Mock
    UserService userService;

    @InjectMocks
    CommonUserRequestValidations commonUserRequestValidations;

    /*********************************** validateFirstName ***********************************/
    @Test
    @DisplayName("validateFirstName should not return error code when first name is valid")
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        //given
        String firstName = "John";

        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateFirstName(firstName);

        //then
        assertThat(actualValue).isNotPresent();
    }

    @ParameterizedTest(name = "{index} validateFirstName method should return error code when value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnErrorWhenFirstNameIsNotValid(String firstName) {
        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateFirstName(firstName);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.FIRST_NAME_REQUIRED);
    }

    /*********************************** validatePassword ***********************************/
    @Test
    @DisplayName("validatePassword should not return error code when password is valid")
    void shouldNotReturnErrorWhenPasswordIsValid() {
        //given
        String password = "password1";

        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validatePassword(password);

        //then
        assertThat(actualValue).isNotPresent();
    }

    @ParameterizedTest(name = "{index} validatePassword method should return required error code when value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnErrorWhenPasswordIsEmpty(String password) {
        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validatePassword(password);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.PASSWORD_REQUIRED);
    }

    @ParameterizedTest(name = "{index} validatePassword method should return invalid error code when value is {0}")
    @ValueSource(strings = {"pass", "pass12", "password"})
    void shouldReturnErrorWhenPasswordIsNotValid(String password) {
        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validatePassword(password);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.INVALID_PASSWORD);
    }

    /*********************************** validateEmail ***********************************/
    @Test
    @DisplayName("validateEmail should not return error code when email is valid")
    void shouldNotReturnErrorWhenEmailIsValid() {
        //given
        String email = "test@gmail.com";

        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateEmail(email, 0L);

        //then
        assertThat(actualValue).isNotPresent();
    }

    @ParameterizedTest(name = "{index} validateEmail method should return required error code when value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnErrorWhenEmailIsEmpty(String email) {
        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateEmail(email, 0L);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.EMAIL_REQUIRED);
    }

    @ParameterizedTest(name = "{index} validateEmail method should return invalid error code when value is {0}")
    @ValueSource(strings = {"test", "test@", "test@.", "test@.com"})
    void shouldReturnErrorWhenEmailIsNotValid(String email) {
        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateEmail(email, 0L);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.INVALID_EMAIL);
    }

    @Test
    @DisplayName("validateEmail should return email in use error code when email is used by other user")
    void shouldReturnEmailUsedErrorWhenEmailAlreadyInUse() {
        //given
        String usedEmail = "test@user.com";
        when(userService.isEmailUsed(usedEmail, 0L)).thenReturn(true);

        //when
        Optional<ErrorCode> actualValue = commonUserRequestValidations.validateEmail(usedEmail, 0L);

        //then
        assertThat(actualValue).isPresent();
        assertThat(actualValue.get()).isEqualTo(UserErrorCode.EMAIL_IN_USE);
    }
}

