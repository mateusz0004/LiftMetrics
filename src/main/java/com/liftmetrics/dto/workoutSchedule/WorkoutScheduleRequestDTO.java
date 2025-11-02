package com.liftmetrics.dto.workoutSchedule;

import com.liftmetrics.enums.Type;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WorkoutScheduleRequestDTO {
    @NotBlank
    private String name;
    private Type type;
    private Long userId;
}
