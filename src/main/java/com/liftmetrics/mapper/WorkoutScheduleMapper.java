package com.liftmetrics.mapper;

import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleRequestDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleResponseDTO;
import com.liftmetrics.model.WorkoutSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WorkoutSessionMapper.class})
public interface WorkoutScheduleMapper {
    @Mapping(source = "userId", target = "user.id")
    WorkoutSchedule toEntity(WorkoutScheduleRequestDTO dto);
    @Mapping(source = "user.id", target = "userId")
    WorkoutScheduleResponseDTO toResponseDto(WorkoutSchedule entity);
}
