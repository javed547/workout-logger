package com.java.lambda.service;

import com.java.lambda.utility.WorkoutLoggerTestConstant;
import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import com.javed.lambda.repository.WorkOutLogRepository;
import com.javed.lambda.service.WorkOutLogServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class WorkOutLogServiceImplTest {

    @InjectMocks
    private WorkOutLogServiceImpl subjectUnderTest = new WorkOutLogServiceImpl();

    @Mock
    private WorkOutLogRepository workOutLogRepository;

    private WorkOut workOut;
    private WorkOutList workOutList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setUpTestData();
    }

    private void setUpTestData() {
        workOut = new WorkOut(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.muscleGroup, WorkoutLoggerTestConstant.exercise,
                WorkoutLoggerTestConstant.setNo, WorkoutLoggerTestConstant.repitations, WorkoutLoggerTestConstant.date);

        workOutList = new WorkOutList();
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        workOuts.add(workOut);
        workOuts.add(workOut);
        workOutList.setWorkOuts(workOuts);
    }

    @Test
    public void test_log_workout() {
        Mockito.when(workOutLogRepository.logWorkOut(workOut))
                .thenReturn(workOut);

        WorkOut logWorkOutResponse = subjectUnderTest.logWorkOut(workOut);
        Assert.assertEquals(WorkoutLoggerTestConstant.userName, logWorkOutResponse.getUsername());
    }

    @Test
    public void test_get_workout() {
        Mockito.when(workOutLogRepository.getWorkOutDetail(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.pageSize,
                WorkoutLoggerTestConstant.pageNumber, WorkoutLoggerTestConstant.date))
                .thenReturn(workOutList);

        WorkOutList getWorkOutResponse = subjectUnderTest.getWorkOutDetail(WorkoutLoggerTestConstant.userName, WorkoutLoggerTestConstant.pageSize,
                WorkoutLoggerTestConstant.pageNumber, WorkoutLoggerTestConstant.date);
        Assert.assertEquals(2, getWorkOutResponse.getWorkOuts().size());
    }
}
