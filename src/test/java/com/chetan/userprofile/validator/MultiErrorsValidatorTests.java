package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.exception.ValidationFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MultiErrorsValidatorTests {

    @Spy
    MultiErrorsValidator<String> underTest;

    @Test
    @DisplayName("validate method should not throw any exception when input is valid")
    void validateMethodShouldNotThrowExceptionWhenInputIsValid() {
        //given
        String input = "valid-input";
        when(underTest.validateAndReturnErrors(input)).thenReturn(Collections.emptyList());

        //then
        assertThatNoException()
                .isThrownBy(() -> {
                    //when
                    underTest.validate(input);
                });
        verify(underTest).validate(input);
    }

    @Test
    @DisplayName("validate method should throw exception when input is not valid")
    void validateMethodShouldThrowExceptionWhenInputIsNotValid() {
        //given
        String input = "invalid-input";
        List<ErrorCode> errors = List.of(DummyErrorCode.INVALID_INPUT);
        when(underTest.validateAndReturnErrors(input)).thenReturn(errors);


        //then
        assertThatExceptionOfType(ValidationFailedException.class)
                .isThrownBy(() -> {
                    //when
                    underTest.validate(input);
                });
        verify(underTest).validate(input);
    }
}
