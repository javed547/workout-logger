package com.javed.lambda.repository;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;

/**
 * The interface User repository.
 */
public interface UserRepository {

    /**
     * inserts users to workout-api-logger application
     *
     * @param @{@link User}
     * @return @{@link User} user
     */
    public User signup(User user);

    /**
     * check user credentials against application to authenticate users
     *
     * @param @{@link Credential}
     * @return @{@link Credential}
     */
    Credential signin(Credential credential);

    /**
     * list user available in application
     *
     * @return @{@link UserList}
     */
    UserList listUser();
}
