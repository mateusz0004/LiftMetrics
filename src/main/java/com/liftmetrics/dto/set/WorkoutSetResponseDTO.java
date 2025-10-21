package com.liftmetrics.dto.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSetResponseDTO {
    private Long id;
    private double weight;
    private int reps;
    private int rpe;
}
