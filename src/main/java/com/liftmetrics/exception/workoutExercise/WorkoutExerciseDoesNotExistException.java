package com.liftmetrics.exception.workoutExercise;

public class WorkoutExerciseDoesNotExistException extends RuntimeException {
    public WorkoutExerciseDoesNotExistException(String message) {
        super(message);
    }
}
