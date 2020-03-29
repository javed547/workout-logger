package com.javed.lambda.service;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import org.springframework.stereotype.Service;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * inserts users to workout-api-logger application
     *
     * @param user the user
     * @return @{@link User} user
     */
    public User signup(User user);

    /**
     * check user credentials against application to authenticate users
     *
     * @param @{@link Credential} credential
     * @return @{@link Credential}
     */
    Credential signin(Credential credential);
}
