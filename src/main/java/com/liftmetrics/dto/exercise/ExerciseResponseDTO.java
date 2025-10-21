package com.liftmetrics.dto.exercise;

import com.liftmetrics.enums.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponseDTO {
    private Long id;
    private String name;
    private MuscleGroup muscleGroup;
}
