package com.chetan.userprofile.user.validator;

import com.chetan.userprofile.error.ErrorCode;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.error.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserRequestValidatorTest {

    @Mock
    CommonUserRequestValidations commonUserRequestValidations;

    @InjectMocks
    CreateUserRequestValidator underTest;

    @Test
    @DisplayName("validateAndReturnErrors method should return empty list when request is valid")
    void shouldReturnEmptyListWhenRequestIsValid() {
        //given
        UserDTO userDTO = createUserDTO();

        when(commonUserRequestValidations.validateFirstName(userDTO.getFirstName()))
                .thenReturn(Optional.empty());
        when(commonUserRequestValidations.validatePassword(userDTO.getPassword()))
                .thenReturn(Optional.empty());
        when(commonUserRequestValidations.validateEmail(userDTO.getEmail(), 0L))
                .thenReturn(Optional.empty());

        //when
        List<ErrorCode> errors = underTest.validateAndReturnErrors(userDTO);

        //then
        assertThat(errors.isEmpty()).isTrue();
        verify(commonUserRequestValidations).validateFirstName(userDTO.getFirstName());
        verify(commonUserRequestValidations).validatePassword(userDTO.getPassword());
        verify(commonUserRequestValidations).validateEmail(userDTO.getEmail(), 0L);
    }

    @Test
    @DisplayName("validateAndReturnErrors method should return list of error(s) when request is not valid")
    void shouldReturnEmptyListWhenRequestIsNotValid() {
        //given
        UserDTO userDTO = createUserDTO();
        userDTO.setEmail("");
        when(commonUserRequestValidations.validateFirstName(userDTO.getFirstName()))
                .thenReturn(Optional.empty());
        when(commonUserRequestValidations.validatePassword(userDTO.getPassword()))
                .thenReturn(Optional.empty());
        when(commonUserRequestValidations.validateEmail(userDTO.getEmail(), 0L))
                .thenReturn(Optional.of(UserErrorCode.EMAIL_REQUIRED));

        //when
        List<ErrorCode> errors = underTest.validateAndReturnErrors(userDTO);

        //then
        assertThat(errors.isEmpty()).isFalse();
        verify(commonUserRequestValidations).validateFirstName(userDTO.getFirstName());
        verify(commonUserRequestValidations).validatePassword(userDTO.getPassword());
        verify(commonUserRequestValidations).validateEmail(userDTO.getEmail(), 0L);
    }

    private UserDTO createUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setEmail("testuser@test.com");
        userDTO.setPassword("password1");
        return userDTO;
    }


}
