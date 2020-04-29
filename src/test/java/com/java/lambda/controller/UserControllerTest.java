package com.java.lambda.controller;

import com.java.lambda.utility.WorkoutLoggerTestConstant;
import com.javed.lambda.controller.UserController;
import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    @InjectMocks
    private UserController subjectUnderTest = new UserController();

    @Mock
    private UserService userService;

    private ResponseEntity<User> signUpResponse;
    private ResponseEntity<Credential> signInResponse;
    private ResponseEntity<UserList> users;
    private User user;
    private Credential credential;
    private UserList userList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setUpTestData();
    }

    private void setUpTestData() {
        user = new User(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.password, WorkoutLoggerTestConstant.firstName,
                WorkoutLoggerTestConstant.lastName, WorkoutLoggerTestConstant.email, WorkoutLoggerTestConstant.address,
                WorkoutLoggerTestConstant.phoneNo);

        credential = new Credential(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.password);

        userList = new UserList();
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user);
        userList.setUsers(users);
    }

    @Test
    public void test_signup() {
        Mockito.when(userService.signup(user))
                .thenReturn(user);

        signUpResponse = subjectUnderTest.signup(user);
        Assert.assertEquals(HttpStatus.OK, signUpResponse.getStatusCode());
    }

    @Test
    public void test_successfull_signin() {
        Mockito.when(userService.signin(credential))
                .thenReturn(credential);

        signInResponse = subjectUnderTest.signin(credential);
        Assert.assertEquals(HttpStatus.OK, signInResponse.getStatusCode());
    }

    @Test
    public void test_failed_signin() {
        Mockito.when(userService.signin(credential))
                .thenReturn(new Credential(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.wrongPassword));

        signInResponse = subjectUnderTest.signin(credential);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, signInResponse.getStatusCode());
    }

    @Test
    public void test_user_list() {
        Mockito.when(userService.listUser())
                .thenReturn(userList);

        users = subjectUnderTest.userList();
        Assert.assertEquals(HttpStatus.OK, users.getStatusCode());
    }
}
