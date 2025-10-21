package com.liftmetrics.dto.exercise;

import com.liftmetrics.enums.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequestDTO {
    @NotBlank
    private String name;

    @NotNull(message = "MuscleGroup cannot be null")
    private MuscleGroup muscleGroup;
}
