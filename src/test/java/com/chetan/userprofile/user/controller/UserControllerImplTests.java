package com.chetan.userprofile.user.controller;

import com.chetan.userprofile.exception.ValidationFailedException;
import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.entity.User;
import com.chetan.userprofile.user.service.UserService;
import com.chetan.userprofile.validator.MultiErrorsValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerImplTests {

    @Mock
    UserService userService;

    @Mock
    MultiErrorsValidator<UserDTO> createUserRequestValidator;

    @InjectMocks
    UserControllerImpl userControllerImpl;

    @Test
    @DisplayName("createUser method should not throw any exception if request is valid")
    void shouldCreateUserIfRequestIsValid() {
        //given
        UserDTO userDTO = new UserDTO();

        //when
        userControllerImpl.createUser(userDTO);

        //then
        verify(createUserRequestValidator).validate(userDTO);
        verify(userService).save(any(User.class));
    }

    @Test
    @DisplayName("createUser method should throw exception if request is not valid")
    void shouldNotCreateUserIfRequestIsNotValid() {
        //given
        UserDTO userDTO = new UserDTO();
        doThrow(ValidationFailedException.class)
                .when(createUserRequestValidator).validate(userDTO);

        //then
        assertThatExceptionOfType(ValidationFailedException.class)
                .isThrownBy(() -> {
                    //when
                    userControllerImpl.createUser(userDTO);
                });
        //then
        verify(createUserRequestValidator).validate(userDTO);
        verify(userService, times(0)).save(any(User.class));
    }
}
