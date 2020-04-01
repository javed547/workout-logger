package com.javed.lambda.model;

import java.util.List;

public class WorkOutList {

    private List<WorkOut> workOuts;

    public WorkOutList() {
    }

    public WorkOutList(List<WorkOut> workOuts) {
        this.workOuts = workOuts;
    }

    public List<WorkOut> getWorkOuts() {
        return workOuts;
    }

    public void setWorkOuts(List<WorkOut> workOuts) {
        this.workOuts = workOuts;
    }

    @Override
    public String toString() {
        return "WorkOutLIst{" +
                "workOuts=" + workOuts +
                '}';
    }
}
