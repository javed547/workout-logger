package com.java.lambda.service;

import com.java.lambda.utility.WorkoutLoggerTestConstant;
import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.UserList;
import com.javed.lambda.repository.UserRepository;
import com.javed.lambda.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl subjectUnderTest = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    private User user;
    private Credential credential;
    private UserList userList;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        setUpTestData();
    }

    private void setUpTestData(){
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
    public void test_signup(){
        Mockito.when(userRepository.signup(user))
                .thenReturn(user);

        User singUpResponse = subjectUnderTest.signup(user);
        Assert.assertEquals(WorkoutLoggerTestConstant.userName, singUpResponse.getUsername());
    }

    @Test
    public void test_successfull_signin(){
        Mockito.when(userRepository.signin(credential))
                .thenReturn(credential);

        Credential singInResponse = subjectUnderTest.signin(credential);
        Assert.assertEquals(WorkoutLoggerTestConstant.userName, singInResponse.getUsername());
    }

    @Test
    public void test_failed_signin(){
        Mockito.when(userRepository.signin(credential))
                .thenReturn(new Credential(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.wrongPassword));

        Credential singInResponse = subjectUnderTest.signin(credential);
        Assert.assertNotEquals(WorkoutLoggerTestConstant.password, singInResponse.getPassword());
    }

    @Test
    public void test_user_list(){
        Mockito.when(userRepository.listUser())
                .thenReturn(userList);

        UserList userList = subjectUnderTest.listUser();
        Assert.assertEquals(2, userList.getUsers().size());
    }

}
