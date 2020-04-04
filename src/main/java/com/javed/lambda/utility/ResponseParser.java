package com.javed.lambda.utility;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseParser {

    private static final Logger logger = LoggerFactory.getLogger(ResponseParser.class);

    public static HashMap<String, AttributeValue> prepareUserInsert(HashMap<String, AttributeValue> itemValues, User user) {
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

    public static Credential castToUsers(List<Map<String, AttributeValue>> items) {
        Credential credential = new Credential();
        credential.setUsername(items.get(0).get("username").s());
        credential.setPassword(items.get(0).get("password").s());
        logger.debug("credential object created with details as {}", credential.toString());
        return credential;
    }

}
