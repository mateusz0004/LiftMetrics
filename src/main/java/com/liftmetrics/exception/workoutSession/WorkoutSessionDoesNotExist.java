package com.liftmetrics.exception.workoutSession;

public class WorkoutSessionDoesNotExist extends RuntimeException {
    public WorkoutSessionDoesNotExist(String message) {
        super(message);
    }
}
