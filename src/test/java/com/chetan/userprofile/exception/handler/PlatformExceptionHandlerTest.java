package com.chetan.userprofile.exception.handler;

import com.chetan.userprofile.error.dto.ErrorDTO;
import com.chetan.userprofile.exception.ValidationFailedException;
import com.chetan.userprofile.validator.DummyErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PlatformExceptionHandlerTest {

    @Test
    @DisplayName("requestValidationFailedHandler method should return list of errors")
    void shouldReturnListOfErrors() {

        //given
        PlatformExceptionHandler platformExceptionHandler = new PlatformExceptionHandler();
        ValidationFailedException e = new ValidationFailedException(List.of(DummyErrorCode.INVALID_INPUT));

        //when
        List<ErrorDTO> errors = platformExceptionHandler.requestValidationFailedHandler(e);

        //then
        assertThat(errors.isEmpty()).isFalse();
        assertThat(errors.get(0).getCode()).isEqualTo(DummyErrorCode.INVALID_INPUT.getCode());
    }
}
