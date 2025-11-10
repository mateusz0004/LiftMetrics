package com.liftmetrics.dto.session;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionRequestDTO {
    @NotBlank(message = "name must not be null")
    private String name;
    @NotNull(message = "workoutSessionId must not be null")
    private Long workoutScheduleId;
}