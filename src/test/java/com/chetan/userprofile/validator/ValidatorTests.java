package com.chetan.userprofile.validator;

import com.chetan.userprofile.error.Error;
import com.chetan.userprofile.exception.ValidationFailedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidatorTests {

    @Spy
    Validator<String> underTest;

    @Test
    @DisplayName("ValidateAndThrowException method should not throw any exception when input is valid")
    void ValidateAndThrowExceptionMethodShouldNotThrowExceptionWhenInputIsValid() {
        //given
        String input = "valid-input";
        when(underTest.validate(input)).thenReturn(Optional.empty());

        //then
        Assertions
                .assertThatNoException()
                .isThrownBy(() -> {
                    //when
                    underTest.validateAndThrowException(input);
                });
        verify(underTest).validate(input);
    }

    @Test
    @DisplayName("ValidateAndThrowException method should throw exception when input is not valid")
    void ValidateAndThrowExceptionMethodShouldThrowExceptionWhenInputIsNotValid() {
        //given
        String input = "invalid-input";
        Optional<List<Error>> errors = Optional.of(List.of(new Error(0, "Error")));
        when(underTest.validate(input)).thenReturn(errors);


        //then
        Assertions
                .assertThatExceptionOfType(ValidationFailedException.class)
                .isThrownBy(() -> {
                    //when
                    underTest.validateAndThrowException(input);
                });
        verify(underTest).validate(input);
    }

    @Test
    @DisplayName("createErrorList should return list of errors")
    void createErrorListShouldReturnListOfErrors() {
        //given
        Optional<Error> error = Optional.of(new Error(0, "Error"));

        //when
        Optional<List<Error>> errors = underTest.createErrorList(error);

        //then
        Assertions.assertThat(errors).isPresent();
        Assertions.assertThat(errors.get().size()).isEqualTo(1);
    }

}
