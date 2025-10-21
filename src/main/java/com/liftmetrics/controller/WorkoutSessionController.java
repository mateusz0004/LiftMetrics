package com.liftmetrics.controller;

import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutSession;
import com.liftmetrics.service.WorkoutSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    public WorkoutSessionController(WorkoutSessionService workoutSessionService) {
        this.workoutSessionService = workoutSessionService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSession>> getAllWorkoutSessions() {
        return ResponseEntity.ok(workoutSessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSession> getWorkoutSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionById(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutSession> addWorkoutSession(@Valid @RequestBody WorkoutSession workoutSession) {
        WorkoutSession created = workoutSessionService.addWorkoutSession(workoutSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutSession> updateWorkoutSession(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutSession workoutSession
    ) {
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSession(id, workoutSession));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSession(@PathVariable Long id) {
        workoutSessionService.deleteWorkoutSession(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sessionId}/exercises")
    public ResponseEntity<WorkoutSession> addExerciseToWorkoutSession(
            @PathVariable Long sessionId,
            @Valid @RequestBody WorkoutExercise workoutExercise
    ) {
        WorkoutSession updated = workoutSessionService.addExerciseToWorkoutSession(sessionId, workoutExercise);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{sessionId}/exercises/{exerciseId}")
    public ResponseEntity<Void> deleteExerciseFromWorkoutSession(
            @PathVariable Long sessionId,
            @PathVariable Long exerciseId
    ) {
        workoutSessionService.deleteExerciseFromWorkoutSession(exerciseId, sessionId);
        return ResponseEntity.noContent().build();
    }
}