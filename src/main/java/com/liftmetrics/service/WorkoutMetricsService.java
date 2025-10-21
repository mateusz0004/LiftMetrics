package com.liftmetrics.service;

import com.liftmetrics.exception.workoutExercise.WorkoutExerciseDoesNotExistException;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.WorkoutExerciseRepository;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class WorkoutMetricsService {
    private WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutMetricsService(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public double volumeForWorkoutExercise (Long id){
        WorkoutExercise workoutExercise = workoutExerciseRepository
                .findById(id).orElseThrow(() -> new WorkoutExerciseDoesNotExistException("Workout doesn't exist"));
        List<WorkoutSet> sets = workoutExercise.getWorkoutSetList();

        return Optional.ofNullable(workoutExercise.getWorkoutSetList())
                .orElse(List.of())
                .stream()
                .mapToDouble(set -> set.getWeight() * set.getReps())
                .sum();
    }
    }
