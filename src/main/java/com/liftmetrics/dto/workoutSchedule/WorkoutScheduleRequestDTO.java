package com.liftmetrics.dto.workoutSchedule;

import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
//import com.liftmetrics.dto.user.UserRequestDTO;
import com.liftmetrics.enums.Type;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutScheduleRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private Type type;

    @Valid
    private List<WorkoutSessionRequestDTO> workoutSessionList;
    //private UserRequestDTO user;
}
