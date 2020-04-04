package com.javed.lambda.repository;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.utility.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Value("${dynamodb.table.name}")
    private String tableName;

    /**
     * inserts users to workout-api-logger application
     *
     * @param user@return @{@link User} user
     */
    @Override
    public User signup(User user) {
        DynamoDbClient dynamoDbClient = null;
        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        ResponseParser.prepareUserInsert(itemValues, user);

        PutItemRequest putItemRequest = PutItemRequest
                .builder()
                .tableName(tableName)
                .item(itemValues)
                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            dynamoDbClient.putItem(putItemRequest);
            logger.info("Successfully inserted user data for username : {}", user.getUsername());
        } catch (DynamoDbException e) {
            logger.error("error occurred while adding record to dynamo db : {}", e.getMessage());
        } finally {
            if (null != dynamoDbClient) {
                logger.debug("closing dynamo db client");
                dynamoDbClient.close();
            }
        }

        return user;
    }

    /**
     * check user credentials against application to authenticate users
     *
     * @param credential@return @{@link Credential}
     */
    @Override
    public Credential signin(Credential credential) {
        DynamoDbClient dynamoDbClient = null;
        logger.info("calling dynamoDb table to authenticate username : {}", credential.getUsername());

        Credential credentialResult = null;
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#username", "username");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":usernameValue", AttributeValue.builder().s(credential.getUsername()).build());

        QueryRequest queryRequest = QueryRequest
                .builder()
                .tableName(tableName)
                .keyConditionExpression("#username = :usernameValue")
                .expressionAttributeNames(expressionAttributesNames)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            QueryResponse queryResponse = dynamoDbClient.query(queryRequest);
            credentialResult = ResponseParser.castToUsers(queryResponse.items());
            logger.info("successfully pulled user data for username : {}", credential.getUsername());
        } catch (DynamoDbException e) {
            logger.error("error occurred while retrieving record to dynamo db : {}", e.getMessage());
        } finally {
            if (null != dynamoDbClient) {
                logger.debug("closing dynamo db client");
                dynamoDbClient.close();
            }
        }
        return credentialResult;
    }

    /**
     * list user available in application
     *
     * @return @{@link UserList}
     */
    @Override
    public UserList listUser() {
        List<User> userList = new ArrayList<User>();

        userList.add(new User("mohdJaved", "admin123", "Mohd Javed", "Khan", "jal90javed@gmail.com", "Test Address 1", 123));
        userList.add(new User("mohdJaved", "admin123", "Mohd Javed", "Khan", "jal90javed@gmail.com", "Test Address 1", 123));

        UserList list = new UserList();
        list.setUsers(userList);
        return list;
    }

}
