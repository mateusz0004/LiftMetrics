package com.liftmetrics.dto.workoutExercise;

import com.liftmetrics.dto.exercise.ExerciseResponseDTO;
import com.liftmetrics.dto.set.WorkoutSetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseResponseDTO {
    private Long id;
    private ExerciseResponseDTO exercise;
    private List<WorkoutSetResponseDTO> workoutSetList;
    private int breakInSeconds;
    private Long workoutSessionId;
}
