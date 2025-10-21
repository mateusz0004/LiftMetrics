package com.liftmetrics.dto.workoutSchedule;

import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
// com.liftmetrics.dto.user.UserResponseDTO;
import com.liftmetrics.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutScheduleResponseDTO {
    private Long id;
    private String name;
    private Type type;
    private List<WorkoutSessionResponseDTO> workoutSessionList;
    //private UserResponseDTO user;
}