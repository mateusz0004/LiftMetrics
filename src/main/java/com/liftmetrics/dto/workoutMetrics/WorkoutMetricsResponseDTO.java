package com.liftmetrics.dto.workoutMetrics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutMetricsResponseDTO {
    private Long id;
    private double IWV;
    private double fatigue;
    private double exerciseTotalVolume;
    private Long workoutExerciseId;
}
