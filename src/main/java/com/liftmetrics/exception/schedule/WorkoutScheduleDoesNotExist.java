package com.liftmetrics.exception.schedule;

public class WorkoutScheduleDoesNotExist extends RuntimeException {
    public WorkoutScheduleDoesNotExist(String message) {
        super(message);
    }
}
