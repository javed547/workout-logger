package com.javed.lambda.controller;

import com.javed.lambda.model.User;
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

    /**
     * Insert user response entity.
     *
     * @param userName the user name
     * @return the response entity
     */
    @PostMapping(value = "/users/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> insertUser(@PathVariable("id") String userName) {

        DynamoDbClient dynamoDbClient = null;
        User user = null;
        logger.debug("calling dynamoDb table for username : {}", userName);

        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#username", "username");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":usernameValue", AttributeValue.builder().s(userName).build());

        QueryRequest queryRequest = QueryRequest
                .builder()
                .tableName("users")
                .keyConditionExpression("#username = :usernameValue")
                .expressionAttributeNames(expressionAttributesNames)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            QueryResponse queryResponse = dynamoDbClient.query(queryRequest);
            user = castToUsers(queryResponse.items());
            logger.debug("successfully pulled user data for username : {}", userName);
        } catch (DynamoDbException e) {
            logger.error("error occurred while adding record to dynamo db : {}", e.getMessage());
        } finally {
            if (null != dynamoDbClient) {
                logger.debug("closing dynamo db client");
                dynamoDbClient.close();
            }
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    private User castToUsers(List<Map<String, AttributeValue>> items) {
        User users = new User();
        users.setUsername(items.get(0).get("username").s());
        users.setPassword(items.get(0).get("password").s());
        users.setFirstname(items.get(0).get("firstname").s());
        users.setLastname(items.get(0).get("lastname").s());
        users.setEmail(items.get(0).get("email").s());
        users.setAddress(items.get(0).get("address").s());
        users.setPhone(Integer.parseInt(items.get(0).get("phone").s()));
        return users;
    }
}
