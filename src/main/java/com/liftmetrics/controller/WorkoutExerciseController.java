package com.liftmetrics.controller;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.dto.set.WorkoutSetRequestDTO;
import com.liftmetrics.dto.set.WorkoutSetResponseDTO;
import com.liftmetrics.mapper.WorkoutExerciseMapper;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/workout-exercise")
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService, WorkoutExerciseMapper workoutExerciseMapper) {
        this.workoutExerciseService = workoutExerciseService;
        this.workoutExerciseMapper = workoutExerciseMapper;
    }
    @GetMapping
    public ResponseEntity<List<WorkoutExerciseResponseDTO>> getAllWorkoutExercise(){
        return ResponseEntity.ok(workoutExerciseService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutExerciseResponseDTO> getWorkoutExerciseById(@PathVariable Long id) {
        WorkoutExercise exercise = workoutExerciseService.getWorkoutExerciseById(id);
        WorkoutExerciseResponseDTO response = workoutExerciseMapper.toResponseDto(exercise);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<WorkoutExerciseResponseDTO> addWorkoutExercise(
            @Valid @RequestBody WorkoutExerciseRequestDTO dto) {

        WorkoutExercise exercise = workoutExerciseMapper.toEntity(dto);
        WorkoutExercise created = workoutExerciseService.addExercise(exercise);
        WorkoutExerciseResponseDTO response = workoutExerciseMapper.toResponseDto(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{exerciseId}")
    public ResponseEntity<WorkoutExerciseResponseDTO> updateWorkoutExercise(
            @PathVariable Long exerciseId,
            @Valid @RequestBody WorkoutExerciseRequestDTO dto) {

        WorkoutExercise updatedEntity = workoutExerciseMapper.toEntity(dto);
        WorkoutExercise updated = workoutExerciseService.updateExercise(exerciseId, updatedEntity);
        WorkoutExerciseResponseDTO response = workoutExerciseMapper.toResponseDto(updated);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable Long exerciseId) {
        workoutExerciseService.deleteWorkoutExercise(exerciseId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{exerciseId}/sets")
    public ResponseEntity<WorkoutSetResponseDTO> addSetToExercise(
            @PathVariable Long exerciseId,
            @Valid @RequestBody WorkoutSetRequestDTO dto) {

        WorkoutSetResponseDTO createdSet = workoutExerciseService.addSetToWorkoutExercise(exerciseId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSet);
    }
    @DeleteMapping("/{exerciseId}/sets/{setId}")
    public ResponseEntity<Void> deleteSetFromExercise(
            @PathVariable Long exerciseId,
            @PathVariable Long setId) {

        workoutExerciseService.deleteSetFromWorkoutExercise(exerciseId, setId);
        return ResponseEntity.noContent().build();
    }
}
