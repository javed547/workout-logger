package com.javed.lambda.controller;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Signup response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PutMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> signup(@RequestBody User user) {
        User resultUser = userService.signup(user);
        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

    /**
     * Signin response entity.
     *
     * @param credential the credential
     * @return the response entity
     */
    @PostMapping(value = "/signin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Credential> signin(@RequestBody Credential credential) {
        Credential credentialResult = userService.signin(credential);
        if (credentialResult.getPassword().compareTo(credential.getPassword()) == 0) {
            return new ResponseEntity<Credential>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Credential>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * list user available in application
     *
     * @return @{@link ResponseEntity}
     */
    @GetMapping(value = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserList> userList(){
        UserList list = userService.listUser();
        return new ResponseEntity<UserList>(list, HttpStatus.OK);
    }
}
