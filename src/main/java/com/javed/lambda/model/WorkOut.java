package com.javed.lambda.model;

public class WorkOut {

    private String username;
    private String muscleGroup;
    private String exercise;
    private Integer setNo;
    private Integer repetitions;
    private String date;

    public WorkOut() {
    }

    public WorkOut(String username, String muscleGroup, String exercise, Integer setNo, Integer repitations, String date) {
        this.username = username;
        this.muscleGroup = muscleGroup;
        this.exercise = exercise;
        this.setNo = setNo;
        this.repetitions = repitations;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getSetNo() {
        return setNo;
    }

    public void setSetNo(Integer setNo) {
        this.setNo = setNo;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WorkOut{" +
                "username='" + username + '\'' +
                ", muscleGroup='" + muscleGroup + '\'' +
                ", exercise='" + exercise + '\'' +
                ", setNo=" + setNo +
                ", repetitions=" + repetitions +
                ", date='" + date + '\'' +
                '}';
    }
}
