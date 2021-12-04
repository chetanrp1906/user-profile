package com.chetan.userprofile.user.service;

import com.chetan.userprofile.user.entity.User;
import com.chetan.userprofile.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("can save the user")
    void shouldSaveUser() {
        //given
        User user = new User();

        //when
        userServiceImpl.save(user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("isEmailUsed method should return true if email is already used")
    void shouldReturnTrueIfEmailIsUsed() {
        //given
        String email = "test@email.com";
        long usedId = 1L;
        when(userRepository.countEmailOccurrence(email, usedId)).thenReturn(1L);

        //when
        boolean actualValue = userServiceImpl.isEmailUsed(email, usedId);

        //then
        assertThat(actualValue).isTrue();
        verify(userRepository).countEmailOccurrence(email, usedId);
    }

    @Test
    @DisplayName("isEmailUsed method should return false if email is not used")
    void shouldReturnFalseIfEmailIsNotUsed() {
        //given
        String email = "test@email.com";
        long usedId = 0L;
        when(userRepository.countEmailOccurrence(email, usedId)).thenReturn(0L);

        //when
        boolean actualValue = userServiceImpl.isEmailUsed(email, usedId);

        //then
        assertThat(actualValue).isFalse();
        verify(userRepository).countEmailOccurrence(email, usedId);
    }
}
