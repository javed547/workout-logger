package com.javed.lambda.repository;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;

/**
 * The interface User repository.
 */
public interface UserRepository {

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
