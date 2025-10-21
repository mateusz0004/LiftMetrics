package com.liftmetrics.dto.session;

import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponseDTO {
    private Long id;
    private String name;
    private List<WorkoutExerciseResponseDTO> workoutExercisesList;
}