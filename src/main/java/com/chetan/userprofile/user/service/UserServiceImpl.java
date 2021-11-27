package com.chetan.userprofile.user.service;

import com.chetan.userprofile.user.entity.User;
import com.chetan.userprofile.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isEmailUsed(String email, Long userId) {
        return userRepository.countEmailOccurrence(email, userId) > 0;
    }
}
