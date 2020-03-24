package com.javed.lambda.repository;

import com.javed.lambda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Value("${dynamodb.table.name}")
    private String tableName;

    private DynamoDbClient dynamoDbClient;

    /**
     * inserts users to workout-api-logger application
     *
     * @param user@return @{@link User} user
     */
    @Override
    public User signup(User user) {
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

        return user;
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

}
