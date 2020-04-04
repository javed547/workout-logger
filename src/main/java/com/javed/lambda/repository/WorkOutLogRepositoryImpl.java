package com.javed.lambda.repository;

import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkOutLogRepositoryImpl implements WorkOutLogRepository {

    /**
     * log workout for logged in username.
     *
     * @param @{@link WorkOut}
     * @return @{@link WorkOut}
     */
    @Override
    public WorkOut logWorkOut(WorkOut workOut) {
        return new WorkOut("javed547", "Chest", "Parallel Chest Press", 2, 10, "22-12-2019");
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
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        workOuts.add(new WorkOut("javed547", "Chest", "Parallel Chest Press", 3, 15, "22-12-2019"));
        workOuts.add(new WorkOut("javed547", "Chest", "Parallel Chest Press", 4, 15, "22-12-2019")) ;
        WorkOutList workOutList = new WorkOutList();
        workOutList.setWorkOuts(workOuts);
        return workOutList;
    }
}
