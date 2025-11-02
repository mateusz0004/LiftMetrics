package com.liftmetrics.service;
import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsRequestDTO;
import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsResponseDTO;
import com.liftmetrics.exception.set.ListOfSetsDoesNotExistException;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutMetrics;
import com.liftmetrics.model.WorkoutSet;
import com.liftmetrics.repository.WorkoutExerciseRepository;
import com.liftmetrics.repository.WorkoutMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutMetricsService {

    private final WorkoutMetricsRepository workoutMetricsRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    public List<WorkoutMetricsResponseDTO> getAllMetrics() {
        return workoutMetricsRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WorkoutMetricsResponseDTO getMetricsById(Long id) {
        return workoutMetricsRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Workout metrics not found"));
    }

    public WorkoutMetricsResponseDTO addMetrics(WorkoutMetricsRequestDTO dto) {
        WorkoutExercise exercise = workoutExerciseRepository.findById(dto.getWorkoutExerciseId())
                .orElseThrow(() -> new RuntimeException("Workout exercise not found"));

        List<WorkoutSet> sets = exercise.getWorkoutSetList();
        if (sets == null || sets.isEmpty()) {
            throw new ListOfSetsDoesNotExistException("No sets available for this exercise");
        }

        double totalVolume = calculateTotalVolume(sets);
        double IWV = calculateIWV(sets);
        double fatigue = calculateFatigue(sets);

        WorkoutMetrics metrics = WorkoutMetrics.builder()
                .exerciseTotalVolume(totalVolume)
                .IWV(IWV)
                .fatigue(fatigue)
                .workoutExercise(exercise)
                .build();

        WorkoutMetrics saved = workoutMetricsRepository.save(metrics);
        return mapToResponse(saved);
    }

    public void deleteMetrics(Long id) {
        workoutMetricsRepository.deleteById(id);
    }

    private double calculateTotalVolume(List<WorkoutSet> sets) {
        return sets.stream()
                .mapToDouble(set -> set.getWeight() * set.getReps())
                .sum();
    }

    private double calculateIWV(List<WorkoutSet> sets) {
        return calculateTotalVolume(sets) / sets.size();
    }

    private double calculateFatigue(List<WorkoutSet> sets) {
        if (sets.size() < 2) return 0.0;

        WorkoutSet topSet = sets.stream()
                .max(Comparator.comparingDouble(WorkoutSet::getWeight))
                .orElseThrow();

        WorkoutSet backoffSet = sets.get(sets.size() - 1);

        double rmTop = calculate1RM(topSet.getWeight(), topSet.getReps());
        double rmBackoff = calculate1RM(backoffSet.getWeight(), backoffSet.getReps());

        return (1 - (rmBackoff / rmTop)) * 100;
    }

    private double calculate1RM(double weight, int reps) {
        return weight * (1 + reps / 30.0);
    }

    private WorkoutMetricsResponseDTO mapToResponse(WorkoutMetrics entity) {
        return new WorkoutMetricsResponseDTO(
                entity.getId(),
                entity.getIWV(),
                entity.getFatigue(),
                entity.getExerciseTotalVolume(),
                entity.getWorkoutExercise() != null ? entity.getWorkoutExercise().getId() : null
        );
    }
}
