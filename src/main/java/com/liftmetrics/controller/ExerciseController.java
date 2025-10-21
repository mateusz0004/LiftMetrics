package com.liftmetrics.controller;

import com.liftmetrics.model.Exercise;
import com.liftmetrics.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.findById(id);
        return ResponseEntity.ok(exercise);
    }

    @PostMapping
    public ResponseEntity<Exercise> addExercise(@Valid @RequestBody Exercise exercise) {
        Exercise created = exerciseService.addExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody Exercise updatedExercise) {

        Exercise exercise = exerciseService.updateExercise(id, updatedExercise);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
