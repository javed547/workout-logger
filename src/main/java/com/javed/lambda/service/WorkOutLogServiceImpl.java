package com.javed.lambda.service;

import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import com.javed.lambda.repository.WorkOutLogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOutLogServiceImpl implements WorkOutLogService {

    private static final Logger logger = LogManager.getLogger(WorkOutLogServiceImpl.class);

    @Autowired
    private WorkOutLogRepository workOutLogRepository;

    /**
     * log workout for logged in username.
     *
     * @param @{@link WorkOut}
     * @return @{@link WorkOut}
     */
    @Override
    public WorkOut logWorkOut(WorkOut workOut) {
        return workOutLogRepository.logWorkOut(workOut);
    }

    /**
     * list workout details for logged in user and search parameter.
     *
     * @param @{@link String} username
     * @param @{@link String} pageSize
     * @param @{@link String} pageNUmber
     * @param @{@link String} date
     * @return @{@link WorkOutList}
     */
    @Override
    public WorkOutList getWorkOutDetail(String username, String pageSize, String pageNumber, String date) {
        return workOutLogRepository.getWorkOutDetail(username, pageSize, pageNumber, date);
    }
}
