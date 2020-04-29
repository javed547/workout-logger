package com.javed.lambda.repository;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.utility.ResponseParser;
import com.javed.lambda.utility.WorkoutLoggerConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    private DynamoDbClient dynamoDbClient;

    /**
     * inserts users to workout-api-logger application
     *
     * @param user@return @{@link User} user
     */
    @Override
    public User signup(User user) {
        logger.info("calling dynamoDb table to insert username : {}", user.getUsername());

        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        ResponseParser.prepareUserInsert(itemValues, user);

        PutItemRequest putItemRequest = PutItemRequest
                .builder()
                .tableName(WorkoutLoggerConstant.usersTable)
                .item(itemValues)
                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            PutItemResponse putItemResponse = dynamoDbClient.putItem(putItemRequest);
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
        logger.info("calling dynamoDb table to authenticate username : {}", credential.getUsername());

        Credential credentialResult = null;
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#username", "username");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":usernameValue", AttributeValue.builder().s(credential.getUsername()).build());

        QueryRequest queryRequest = QueryRequest
                .builder()
                .tableName(WorkoutLoggerConstant.usersTable)
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

        dynamoDbClient = DynamoDbClient.create();
        ScanRequest scanRequest = ScanRequest.builder().tableName(WorkoutLoggerConstant.usersTable).build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        userList = ResponseParser.retrieveUsers(scanResponse);

        UserList list = new UserList();
        list.setUsers(userList);

        return list;
    }

}
