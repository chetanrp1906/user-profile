package com.chetan.userprofile.user.controller;

import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.entity.User;
import com.chetan.userprofile.user.mapper.UserMapper;
import com.chetan.userprofile.user.service.UserService;
import com.chetan.userprofile.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private Validator<UserDTO> createUserRequestValidator;

    public UserControllerImpl(UserService userService, Validator<UserDTO> createUserRequestValidator) {
        this.userService = userService;
        this.createUserRequestValidator = createUserRequestValidator;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(UserDTO userDTO) {
        User user = UserMapper.MAPPER.toUser(userDTO);
        createUserRequestValidator.validateAndThrowException(userDTO);
        userService.save(user);
    }
}
