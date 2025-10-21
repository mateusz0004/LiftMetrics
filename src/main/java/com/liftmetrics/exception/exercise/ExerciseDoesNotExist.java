package com.liftmetrics.exception.exercise;

public class ExerciseDoesNotExist extends RuntimeException {
    public ExerciseDoesNotExist(String message) {
        super(message);
    }
}
