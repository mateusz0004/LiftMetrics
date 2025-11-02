package com.liftmetrics.controller;

import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
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
    public ResponseEntity<List<WorkoutSessionResponseDTO>> getAllWorkoutSessions() {
        return ResponseEntity.ok(workoutSessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSessionResponseDTO> getWorkoutSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionById(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutSessionResponseDTO> addWorkoutSession(@Valid @RequestBody WorkoutSessionRequestDTO workoutSessionRequestDTO) {
        WorkoutSessionResponseDTO created = workoutSessionService.addWorkoutSession(workoutSessionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutSessionResponseDTO> updateWorkoutSession(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutSessionRequestDTO workoutSessionRequestDTO
    ) {
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSession(id, workoutSessionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSession(@PathVariable Long id) {
        workoutSessionService.deleteWorkoutSession(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sessionId}/exercises")
    public ResponseEntity<WorkoutSessionResponseDTO> addExerciseToWorkoutSession(
            @PathVariable Long sessionId,
            @Valid @RequestBody WorkoutExerciseRequestDTO workoutExerciseRequestDTO
    ) {
        WorkoutSessionResponseDTO updated = workoutSessionService.addExerciseToWorkoutSession(sessionId, workoutExerciseRequestDTO);
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