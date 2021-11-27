package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.user.error.UserErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

class ValidationUtilsTests {

    private static final ErrorCode errorCode = UserErrorCode.FIRST_NAME_REQUIRED;

    @ParameterizedTest(name = "{index} isEmpty method should return 'true' if value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnTrueWhenValueIsEmpty(String value) {
        //given
        //when
        Boolean actualValue = ValidationUtils.isEmpty(value);

        //then
        Assertions.assertThat(actualValue).isTrue();
    }

    @Test
    @DisplayName("isEmpty method return 'false' if value is not empty")
    void shouldReturnFalseWhenValueIsNotEmpty() {
        //given
        String value = "value";

        //when
        Boolean actualValue = ValidationUtils.isEmpty(value);

        //then
        Assertions.assertThat(actualValue).isFalse();
    }

    @ParameterizedTest(name = "{index} validateISEmpty method should return error when value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void validateIsEmptyShouldReturnErrorWhenInvalidInput(String value) {
        //given
        //when
        Optional<Error> error = ValidationUtils.validateIsEmpty(value, errorCode);

        //then
        Assertions.assertThat(error).isPresent();
        Assertions.assertThat(error.get().getCode()).isEqualTo(errorCode.getCode());
        Assertions.assertThat(error.get().getDescription()).isEqualTo(errorCode.getDescription());
    }

    @Test
    @DisplayName("validateISEmpty method should not return error when value is not empty")
    void validateIsEmptyShouldNotReturnErrorWhenValidInput() {
        //given
        String value = "value";

        //when
        Optional<Error> error = ValidationUtils.validateIsEmpty(value, errorCode);

        //then
        Assertions.assertThat(error).isNotPresent();
    }
}
