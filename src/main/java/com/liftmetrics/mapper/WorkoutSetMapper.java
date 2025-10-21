package com.liftmetrics.mapper;

import com.liftmetrics.dto.set.WorkoutSetRequestDTO;
import com.liftmetrics.dto.set.WorkoutSetResponseDTO;
import com.liftmetrics.model.WorkoutSet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutSetMapper {
    WorkoutSet toEntity(WorkoutSetRequestDTO dto);
    WorkoutSetResponseDTO toResponseDTO(WorkoutSet entity);
}
