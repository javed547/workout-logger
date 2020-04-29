package com.java.lambda.utility;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.utility.ResponseParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseParserTest {

    @InjectMocks
    private ResponseParser subjectUnderTest = new ResponseParser();

    private HashMap<String, AttributeValue> itemValues;
    private User user;
    private List<Map<String, AttributeValue>> credentialsItemValuesList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setUpTestData();
    }

    private void setUpTestData() {
        itemValues = new HashMap<String, AttributeValue>();
        credentialsItemValuesList = new ArrayList<Map<String, AttributeValue>>();

        user = new User(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.password, WorkoutLoggerTestConstant.firstName,
                WorkoutLoggerTestConstant.lastName, WorkoutLoggerTestConstant.email, WorkoutLoggerTestConstant.address,
                WorkoutLoggerTestConstant.phoneNo);

        itemValues.put("username", AttributeValue.builder().s(user.getUsername()).build());
        itemValues.put("password", AttributeValue.builder().s(user.getPassword()).build());
        credentialsItemValuesList.add(itemValues);
    }

    @Test
    public void test_prepare_user_insert() {
        itemValues = ResponseParser.prepareUserInsert(itemValues, user);
        Assert.assertEquals(WorkoutLoggerTestConstant.userName, (String) itemValues.get("username").s());
    }

    @Test
    public void test_cast_to_users() {
        Credential credential = ResponseParser.castToUsers(credentialsItemValuesList);
        Assert.assertEquals(WorkoutLoggerTestConstant.userName, credential.getUsername());
    }
}
