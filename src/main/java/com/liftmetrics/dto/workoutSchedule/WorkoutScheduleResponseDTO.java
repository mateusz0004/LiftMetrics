package com.liftmetrics.dto.workoutSchedule;

import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
import com.liftmetrics.enums.Type;
import lombok.Data;

import java.util.List;
@Data
public class WorkoutScheduleResponseDTO {
    private Long id;
    private String name;
    private Type type;
    private List<WorkoutSessionResponseDTO> workoutSessionList;
    private Long userId;
}
