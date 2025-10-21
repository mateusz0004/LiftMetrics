package com.liftmetrics.mapper;

import com.liftmetrics.dto.exercise.ExerciseRequestDTO;
import com.liftmetrics.dto.exercise.ExerciseResponseDTO;
import com.liftmetrics.model.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toEntity(ExerciseRequestDTO dto);

    ExerciseResponseDTO toResponseDto(Exercise entity);
}
