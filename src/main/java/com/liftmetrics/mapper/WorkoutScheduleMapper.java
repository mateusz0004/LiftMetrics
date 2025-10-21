package com.liftmetrics.mapper;

import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleRequestDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleResponseDTO;
import com.liftmetrics.model.WorkoutSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WorkoutSessionMapper.class})
public interface WorkoutScheduleMapper {

    WorkoutSchedule toEntity(WorkoutScheduleRequestDTO dto);
    WorkoutScheduleResponseDTO toResponseDto(WorkoutSchedule entity);
}
