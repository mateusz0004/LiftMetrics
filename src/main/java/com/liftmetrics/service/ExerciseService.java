package com.liftmetrics.service;

import com.liftmetrics.exception.exercise.ExerciseDoesNotExist;
import com.liftmetrics.model.Exercise;
import com.liftmetrics.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Exercise findById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseDoesNotExist("Exercise with id " + id + " does not exist"));
    }

    public Exercise addExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Long id, Exercise updatedExercise) {
        Exercise existing = findById(id);
        existing.setName(updatedExercise.getName());
        existing.setMuscleGroup(updatedExercise.getMuscleGroup());
        return exerciseRepository.save(existing);
    }

    public void deleteExercise(Long id) {
        Exercise existing = findById(id);
        exerciseRepository.delete(existing);
    }
}
