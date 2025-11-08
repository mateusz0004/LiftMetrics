package com.liftmetrics.dto.workoutMetrics;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutMetricsRequestDTO {
    @NotNull
    private Long workoutExerciseId;
}
