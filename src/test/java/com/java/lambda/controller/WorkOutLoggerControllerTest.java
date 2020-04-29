package com.java.lambda.controller;

import com.java.lambda.utility.WorkoutLoggerTestConstant;
import com.javed.lambda.controller.WorkOutLogController;
import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import com.javed.lambda.service.WorkOutLogService;
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

public class WorkOutLoggerControllerTest {

    @InjectMocks
    private WorkOutLogController subjectUnderTest = new WorkOutLogController();

    @Mock
    private WorkOutLogService workOutLogService;

    private ResponseEntity<WorkOut> logWorkOutResponse;
    private ResponseEntity<WorkOutList> getWorkOutDetailsResponse;
    private WorkOut workOut;
    private WorkOutList workOutList;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private void setUpTestData(){
        workOut = new WorkOut(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.muscleGroup, WorkoutLoggerTestConstant.exercise,
                WorkoutLoggerTestConstant.setNo, WorkoutLoggerTestConstant.repitations, WorkoutLoggerTestConstant.date);

        workOutList = new WorkOutList();
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        workOuts.add(workOut);
        workOuts.add(workOut);
        workOutList.setWorkOuts(workOuts);
    }

    @Test
    public void test_log_workout(){
        Mockito.when(workOutLogService.logWorkOut(workOut)).thenReturn(workOut);

        logWorkOutResponse = subjectUnderTest.logWorkOut(WorkoutLoggerTestConstant.userName, workOut);
        Assert.assertEquals(HttpStatus.CREATED, logWorkOutResponse.getStatusCode());
    }

    @Test
    public void test_get_workout_details(){
        Mockito.when(workOutLogService.getWorkOutDetail(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.pageSize,
                WorkoutLoggerTestConstant.pageNumber, WorkoutLoggerTestConstant.date))
                .thenReturn(workOutList);

        getWorkOutDetailsResponse = subjectUnderTest.getWorkOutDetail(WorkoutLoggerTestConstant.pageSize, WorkoutLoggerTestConstant.pageNumber,
                WorkoutLoggerTestConstant.date, WorkoutLoggerTestConstant.userName);
        Assert.assertEquals(HttpStatus.OK, getWorkOutDetailsResponse.getStatusCode());
    }

}
