package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ValidationUtilsTests {

    @ParameterizedTest(name = "{index} isEmpty method should return 'true' if value is {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    void shouldReturnTrueWhenValueIsEmpty(String value) {
        //given
        //when
        Boolean actualValue = ValidationUtils.isEmpty(value);

        //then
        assertThat(actualValue).isTrue();
    }

    @Test
    @DisplayName("isEmpty method should return 'false' if value is not empty")
    void shouldReturnFalseWhenValueIsNotEmpty() {
        //given
        String value = "value";

        //when
        Boolean actualValue = ValidationUtils.isEmpty(value);

        //then
        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("extractErrorCodes method should return list of error codes")
    void shouldReturnListOfErrorCodes() {
        //given
        List<Optional<ErrorCode>> optionalErrorCodes
                = List.of(Optional.of(DummyErrorCode.INVALID_INPUT));

        //when
        List<ErrorCode> errorCodes = ValidationUtils.extractErrorCodes(optionalErrorCodes);

        //then
        assertThat(errorCodes.size()).isGreaterThan(0);
        assertThat(errorCodes.get(0)).isEqualTo(DummyErrorCode.INVALID_INPUT);
    }

    @Test
    @DisplayName("extractErrorCodes method should return empty list when error codes are not present")
    void shouldReturnEmptyListOfErrorCodes() {
        //given
        List<Optional<ErrorCode>> optionalErrorCodes
                = List.of(Optional.empty());

        //when
        List<ErrorCode> errorCodes = ValidationUtils.extractErrorCodes(optionalErrorCodes);

        //then
        assertThat(errorCodes.isEmpty()).isTrue();
    }
}
