package com.liftmetrics.mapper;

import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.model.WorkoutExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutExerciseMapper {
@Mapping(source="workoutSessionId", target ="workoutSession.id")
    WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto);
@Mapping(source="workoutSession.id", target="workoutSessionId")
    WorkoutExerciseResponseDTO toResponseDto(WorkoutExercise entity);
}