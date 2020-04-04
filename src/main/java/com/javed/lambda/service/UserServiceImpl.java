package com.javed.lambda.service;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
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

    /**
     * check user credentials against application to authenticate users
     *
     * @param @{@link Credential} credential
     * @return @{@link Credential}
     */
    @Override
    public Credential signin(Credential credential) {
        return userRepository.signin(credential);
    }

    /**
     * list user available in application
     *
     * @return @{@link UserList}
     */
    @Override
    public UserList listUser() {
        return userRepository.listUser();
    }
}
