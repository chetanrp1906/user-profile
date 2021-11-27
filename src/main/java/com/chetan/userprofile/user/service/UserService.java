package com.chetan.userprofile.user.service;

import com.chetan.userprofile.user.entity.User;

public interface UserService {
    void save(User user);

    boolean isEmailUsed(String email, Long userId);
}
