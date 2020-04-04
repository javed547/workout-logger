package com.javed.lambda.repository;

import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;

/**
 * The interface to log workout and list work out for logged in users.
 */
public interface WorkOutLogRepository {

    /**
     * log workout for logged in username.
     *
     * @param @{@link WorkOut}
     * @return @{@link WorkOut}
     */
    WorkOut logWorkOut(WorkOut workOut);

    /**
     * list workout details for logged in user and search parameter.
     *
     * @param @{@link String} username
     * @param @{@link String} pageSize
     * @param @{@link String} pageNUmber
     * @param @{@link String} date
     * @return @{@link WorkOutList}
     */
    WorkOutList getWorkOutDetail(String username,String pageSize, String pageNumber, String date);

}
