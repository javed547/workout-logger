package com.java.lambda.repository;

import com.java.lambda.utility.WorkoutLoggerTestConstant;
import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.repository.UserRepositoryImpl;
import com.javed.lambda.utility.WorkoutLoggerConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DynamoDbClient.class)
public class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl subjectUnderTest = new UserRepositoryImpl();

    @Mock
    private DynamoDbClient dynamoDbClient;

    private HashMap<String, AttributeValue> itemValues = null;
    private User user;
    private PutItemRequest putItemRequest;
    private PutItemResponse putItemResponse;
    private Credential credential;
    private Map<String, String> expressionAttributesNames = new HashMap<>();
    private QueryRequest queryRequest;
    private Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    private QueryResponse queryResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setUpTestData();
    }

    private void setUpTestData() {
        user = new User(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.password, WorkoutLoggerTestConstant.firstName,
                WorkoutLoggerTestConstant.lastName, WorkoutLoggerTestConstant.email, WorkoutLoggerTestConstant.address,
                WorkoutLoggerTestConstant.phoneNo);

        itemValues = new HashMap<String, AttributeValue>();
        itemValues.put("username", AttributeValue.builder().s(user.getUsername()).build());
        itemValues.put("password", AttributeValue.builder().s(user.getPassword()).build());
        itemValues.put("firstname", AttributeValue.builder().s(user.getFirstname()).build());

        itemValues.put("lastname", AttributeValue.builder().s(user.getLastname()).build());
        itemValues.put("email", AttributeValue.builder().s(user.getEmail()).build());
        itemValues.put("address", AttributeValue.builder().s(user.getAddress()).build());

        itemValues.put("phone", AttributeValue.builder().s(String.valueOf(user.getPhone())).build());

        putItemRequest = PutItemRequest
                .builder()
                .tableName(WorkoutLoggerConstant.usersTable)
                .item(itemValues)
                .build();

        putItemResponse.builder();

        credential = new Credential(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.password);
        expressionAttributeValues.put(":usernameValue", AttributeValue.builder().s(credential.getUsername()).build());
        queryRequest = QueryRequest
                    .builder()
                    .tableName(WorkoutLoggerConstant.usersTable)
                    .keyConditionExpression("#username = :usernameValue")
                    .expressionAttributeNames(expressionAttributesNames)
                    .expressionAttributeValues(expressionAttributeValues)
                    .build();

        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("username", AttributeValue.builder().s(credential.getUsername()).build());
        item.put("password", AttributeValue.builder().s(credential.getPassword()).build());
        queryResponse = QueryResponse
                .builder()
                .items(item)
                .build();
        System.out.println();
    }

    @Test
    public void test_signup_user() {
        PowerMockito.mockStatic(DynamoDbClient.class);
        BDDMockito.given(DynamoDbClient.create()).willReturn(dynamoDbClient);
        Mockito.when(dynamoDbClient.putItem(putItemRequest)).thenReturn(putItemResponse);

        User signUpResponse = subjectUnderTest.signup(user);
        Assert.assertEquals(signUpResponse.getUsername(), user.getUsername());
    }

    @Test
    @Ignore
    public void test_signin_user() {
        PowerMockito.mockStatic(DynamoDbClient.class);
        BDDMockito.given(DynamoDbClient.create()).willReturn(dynamoDbClient);
        Mockito.when(dynamoDbClient.query(queryRequest)).thenReturn(queryResponse);

        subjectUnderTest.signin(credential);

    }
}
