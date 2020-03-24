package com.javed.lambda.service;

import com.javed.lambda.model.User;
import com.javed.lambda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * inserts users to workout-api-logger application
     *
     * @param user@return @{@link User} user
     */
    @Override
    public User signup(User user) {
        return userRepository.signup(user);
    }
}
