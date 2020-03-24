package com.javed.lambda.service;

import com.javed.lambda.model.User;
import org.springframework.stereotype.Service;

public interface UserService {

    /**
     * inserts users to workout-api-logger application
     *
     * @param @{@link User} user
     * @return @{@link User} user
     */
    public User signup(User user);

}
