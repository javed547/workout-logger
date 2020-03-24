package com.javed.lambda.repository;

import com.javed.lambda.model.User;

public interface UserRepository {

    /**
     * inserts users to workout-api-logger application
     *
     * @param @{@link User} user
     * @return @{@link User} user
     */
    public User signup(User user);
}
