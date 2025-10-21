package com.liftmetrics.dto.set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSetRequestDTO {
    @Positive(message = "Weight must be positive")
    private double weight;
    @Positive(message = "Reps must be positive")
    private int reps;
    @Min(value = 1, message = "RPE must be at least 1")
    @Max(value = 10, message = "RPE must be at most 10")
    private int rpe;
}
