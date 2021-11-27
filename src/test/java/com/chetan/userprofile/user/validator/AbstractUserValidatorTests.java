package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.user.error.UserErrorCode;
import com.chetan.userprofile.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AbstractUserValidatorTests {

    UserService userService;
    AbstractUserValidator underTest;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        underTest = Mockito.mock(
                AbstractUserValidator.class,
                Mockito.withSettings()
                        .useConstructor(userService)
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    @DisplayName("validateFirstName should not return error when first name is valid")
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        //given
        String firstName = "John";

        //when
        Optional<Error> actualValue = underTest.validateFirstName(firstName);

        //then
        Assertions.assertThat(actualValue).isNotPresent();
    }

    @ParameterizedTest(name = "{index} validateFirstName method should return error when value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnErrorWhenFirstNameIsNotValid(String firstName) {
        //when
        Optional<Error> actualValue = underTest.validateFirstName(firstName);

        //then
        Assertions.assertThat(actualValue).isPresent();
        Error error = actualValue.get();
        Assertions
                .assertThat(error.getCode())
                .isEqualTo(UserErrorCode.FIRST_NAME_REQUIRED.getCode());
    }

}
