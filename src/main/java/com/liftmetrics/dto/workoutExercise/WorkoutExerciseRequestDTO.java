package com.liftmetrics.dto.workoutExercise;

import com.liftmetrics.dto.exercise.ExerciseRequestDTO;
import com.liftmetrics.dto.set.WorkoutSetRequestDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseRequestDTO {
    @NotNull
    private ExerciseRequestDTO exercise;
    private List<WorkoutSetRequestDTO> workoutSetList;

    @Min(0)
    private int breakInSeconds;

    @NotNull
    private Long workoutSessionId;
}
