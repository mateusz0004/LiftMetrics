package com.liftmetrics.mapper;

import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
import com.liftmetrics.model.WorkoutSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WorkoutExerciseMapper.class})
public interface WorkoutSessionMapper {
    WorkoutSession toEntity(WorkoutSessionRequestDTO dto);
    WorkoutSessionResponseDTO toResponseDto(WorkoutSession entity);
}
