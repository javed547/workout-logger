package com.javed.lambda.controller;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Signup response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> signup(@RequestBody User user) {
        User resultUser = userService.signup(user);
        return new ResponseEntity<User>(resultUser, HttpStatus.OK);
    }

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

    @GetMapping(value = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserList> userList(){
        List<User> userList = new ArrayList<User>();

        userList.add(new User("mohdJaved", "admin123", "Mohd Javed", "Khan", "jal90javed@gmail.com", "Test Address 1", 123));
        userList.add(new User("mohdJaved", "admin123", "Mohd Javed", "Khan", "jal90javed@gmail.com", "Test Address 1", 123));

        UserList list = new UserList();
        list.setUsers(userList);

        return new ResponseEntity<UserList>(list, HttpStatus.OK);
    }
}
