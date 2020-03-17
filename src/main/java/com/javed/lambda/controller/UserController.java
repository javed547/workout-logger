package com.javed.lambda.controller;

import com.javed.lambda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class UserController {

    @Value("${dynamodb.table.name}")
    private String tableName;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        logger.debug("sending application health status");
        return new ResponseEntity<String>("Hi. Application is working fine.", HttpStatus.OK);
    }

    @PostMapping(value = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> insertUser(@RequestBody User user) {

        DynamoDbClient dynamoDbClient = null;
        logger.debug("Successfully created DynamoDB client and going to create user with username : {}", user.getUsername());

        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        prepareUserInsert(itemValues, user);

        PutItemRequest putItemRequest = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(itemValues)
                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            dynamoDbClient.putItem(putItemRequest);
            logger.debug("Successfully inserted user data for username : {}", user.getUsername());
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

    private HashMap<String, AttributeValue> prepareUserInsert(HashMap<String, AttributeValue> itemValues, User user) {
        itemValues.put("username", AttributeValue.builder().s(user.getUsername()).build());
        itemValues.put("password", AttributeValue.builder().s(user.getPassword()).build());
        itemValues.put("firstname", AttributeValue.builder().s(user.getFirstname()).build());

        itemValues.put("lastname", AttributeValue.builder().s(user.getLastname()).build());
        itemValues.put("email", AttributeValue.builder().s(user.getEmail()).build());
        itemValues.put("address", AttributeValue.builder().s(user.getAddress()).build());

        itemValues.put("phone", AttributeValue.builder().s(String.valueOf(user.getPhone())).build());
        logger.debug("user object created with details as {}", user.toString());
        return itemValues;
    }

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
