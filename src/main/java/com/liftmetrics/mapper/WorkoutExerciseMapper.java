package com.liftmetrics.mapper;

import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.model.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WorkoutSetMapper.class, ExerciseMapper.class})
public interface WorkoutExerciseMapper {

    WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto);

    WorkoutExerciseResponseDTO toResponseDto(WorkoutExercise entity);
}