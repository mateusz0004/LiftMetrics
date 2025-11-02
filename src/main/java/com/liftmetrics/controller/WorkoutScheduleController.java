package com.liftmetrics.controller;

import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleRequestDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleResponseDTO;
import com.liftmetrics.service.WorkoutScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-schedules")
public class WorkoutScheduleController {

    private final WorkoutScheduleService workoutScheduleService;

    public WorkoutScheduleController(WorkoutScheduleService workoutScheduleService) {
        this.workoutScheduleService = workoutScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutScheduleResponseDTO>> getAllWorkoutSchedules() {
        return ResponseEntity.ok(workoutScheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutScheduleResponseDTO> getWorkoutScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutScheduleService.getWorkoutScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutScheduleResponseDTO> addWorkoutSchedule(
            @Valid @RequestBody WorkoutScheduleRequestDTO workoutScheduleRequestDTO
    ) {
        WorkoutScheduleResponseDTO created = workoutScheduleService.addWorkoutSchedule(workoutScheduleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutScheduleResponseDTO> updateWorkoutSchedule(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutScheduleRequestDTO workoutScheduleRequestDTO
    ) {
        WorkoutScheduleResponseDTO updated = workoutScheduleService.updateWorkoutSchedule(id, workoutScheduleRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSchedule(@PathVariable Long id) {
        workoutScheduleService.deleteWorkoutSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{scheduleId}/sessions")
    public ResponseEntity<WorkoutScheduleResponseDTO> addSessionToWorkoutSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody WorkoutSessionRequestDTO workoutSessionRequestDTO
    ) {
        WorkoutScheduleResponseDTO updated = workoutScheduleService.addSessionToWorkoutSchedule(scheduleId, workoutSessionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{scheduleId}/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSessionFromWorkoutSchedule(
            @PathVariable Long scheduleId,
            @PathVariable Long sessionId
    ) {
        workoutScheduleService.deleteSessionFromWorkoutSchedule(scheduleId, sessionId);
        return ResponseEntity.noContent().build();
    }
}
