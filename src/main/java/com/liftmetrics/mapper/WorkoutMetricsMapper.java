package com.liftmetrics.mapper;

import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsRequestDTO;
import com.liftmetrics.dto.workoutMetrics.WorkoutMetricsResponseDTO;
import com.liftmetrics.model.WorkoutMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutMetricsMapper
{
    @Mapping(source = "workoutExerciseId", target = "workoutExercise.id")
    WorkoutMetrics toEntity(WorkoutMetricsRequestDTO dto);

    @Mapping(source = "workoutExercise.id", target = "workoutExerciseId")
    WorkoutMetricsResponseDTO toResponseDto(WorkoutMetrics entity);
}
