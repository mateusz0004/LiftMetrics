package com.liftmetrics.controller;

import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsRequestDTO;
import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsResponseDTO;
import com.liftmetrics.service.WorkoutMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class WorkoutMetricsController {

    private final WorkoutMetricsService workoutMetricsService;

    @GetMapping
    public ResponseEntity<List<WorkoutMetricsResponseDTO>> getAllMetrics() {
        return ResponseEntity.ok(workoutMetricsService.getAllMetrics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutMetricsResponseDTO> getMetricsById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutMetricsService.getMetricsById(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutMetricsResponseDTO> addMetrics(@RequestBody WorkoutMetricsRequestDTO dto) {
        return ResponseEntity.ok(workoutMetricsService.addMetrics(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetrics(@PathVariable Long id) {
        workoutMetricsService.deleteMetrics(id);
        return ResponseEntity.noContent().build();
    }
}
