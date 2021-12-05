package com.chetan.userprofile.user.controller;

import com.chetan.userprofile.user.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserController {

    @PostMapping
    void createUser(@RequestBody UserDTO userDTO);
}
