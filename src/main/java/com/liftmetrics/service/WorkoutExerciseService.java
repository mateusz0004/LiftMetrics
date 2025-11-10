package com.liftmetrics.service;

import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.dto.set.WorkoutSetRequestDTO;
import com.liftmetrics.dto.set.WorkoutSetResponseDTO;
import com.liftmetrics.exception.exercise.ExerciseDoesNotExist;
import com.liftmetrics.exception.set.SetDoesNotExistException;
import com.liftmetrics.exception.workoutExercise.WorkoutExerciseDoesNotExistException;
import com.liftmetrics.exception.workoutSession.WorkoutSessionDoesNotExist;
import com.liftmetrics.mapper.WorkoutExerciseMapper;
import com.liftmetrics.mapper.WorkoutSetMapper;
import com.liftmetrics.model.*;
import com.liftmetrics.repository.ExerciseRepository;
import com.liftmetrics.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.SetsRepository;
import com.liftmetrics.repository.WorkoutExerciseRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final SetsRepository setsRepository;
    private final WorkoutSetMapper workoutSetMapper;
    private final WorkoutExerciseMapper workoutExerciseMapper;
    private final ExerciseRepository exerciseRepository;
    private final UserService userService;
    private final WorkoutSessionRepository workoutSessionRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, SetsRepository setsRepository, WorkoutSetMapper workoutSetMapper, WorkoutExerciseMapper workoutExerciseMapper, UserService userService
            , ExerciseRepository exerciseRepository, WorkoutSessionRepository workoutSessionRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.setsRepository = setsRepository;
        this.workoutSetMapper = workoutSetMapper;
        this.workoutExerciseMapper = workoutExerciseMapper;
        this.exerciseRepository = exerciseRepository;
        this.userService = userService;
        this.workoutSessionRepository = workoutSessionRepository;
    }

    public List<WorkoutExerciseResponseDTO> findAll() {
        Long currentUserId = userService.getCurrentUser().getId();


        return workoutExerciseRepository.findAllByUserId(currentUserId)
                .stream()
                .map(workoutExerciseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public WorkoutExerciseResponseDTO getWorkoutExerciseById(Long id) {
        Long currentUserId = userService.getCurrentUser().getId();

        return workoutExerciseRepository.findByIdAndUserId(id, currentUserId)
                .map(workoutExerciseMapper::toResponseDto)
                .orElseThrow(() -> new WorkoutExerciseDoesNotExistException("Workout exercise does not exist"));
    }

    public WorkoutExerciseResponseDTO addExercise(WorkoutExerciseRequestDTO dto) {
        Long currentUserId = userService.getCurrentUser().getId();

        Exercise exercise = exerciseRepository.findByName(dto.getExercise().getName())
                .orElseThrow(() -> new ExerciseDoesNotExist("Exercise does not exist"));

        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(dto.getWorkoutSessionId(), currentUserId)
                .orElseThrow(() -> new WorkoutSessionDoesNotExist("This session does not exist"));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setExercise(exercise);
        workoutExercise.setBreakInSeconds(dto.getBreakInSeconds());
        workoutExercise.setWorkoutSession(workoutSession);
        for (WorkoutSetRequestDTO setDTO : dto.getWorkoutSetList()) {
            WorkoutSet set = workoutSetMapper.toEntity(setDTO);
            workoutExercise.addWorkoutSet(set);
        }
        WorkoutExercise saved = workoutExerciseRepository.save(workoutExercise);
        return workoutExerciseMapper.toResponseDto(saved);
    }

    public void deleteWorkoutExercise(Long exerciseId) {
        Long currentUserId = userService.getCurrentUser().getId();
        WorkoutExercise workoutExercise = workoutExerciseRepository.findByIdAndUserId(exerciseId, currentUserId)
                .orElseThrow(()-> new WorkoutExerciseDoesNotExistException("This workoutExercise does not exist"));
        workoutExerciseRepository.delete(workoutExercise);
    }

    public WorkoutExerciseResponseDTO updateExercise(Long exerciseId, WorkoutExerciseRequestDTO dto) {
        Long currentUserId = userService.getCurrentUser().getId();
        Exercise exercise = exerciseRepository.findByName(dto.getExercise().getName())
                .orElseThrow(()-> new ExerciseDoesNotExist("This workout exercise does not exist"));
        WorkoutExercise workoutExercise = workoutExerciseRepository.findByIdAndUserId(exerciseId, currentUserId)
                .orElseThrow(() -> new WorkoutExerciseDoesNotExistException("This workoutExercise does not exist"));
        WorkoutSession workoutSession = workoutSessionRepository.findByIdAndUserId(dto.getWorkoutSessionId(), currentUserId)
                        .orElseThrow(()-> new WorkoutExerciseDoesNotExistException("This workoutSession does not exist"));
        workoutExercise.setExercise((exercise));
        workoutExercise.setWorkoutSession(workoutSession);
        workoutExercise.setBreakInSeconds(dto.getBreakInSeconds());
        workoutExercise.getWorkoutSetList().clear();


        for (WorkoutSetRequestDTO newSet : dto.getWorkoutSetList()) {
            workoutExercise.addWorkoutSet(workoutSetMapper.toEntity(newSet));
        }
        WorkoutExercise saved = workoutExerciseRepository.save(workoutExercise);
        return workoutExerciseMapper.toResponseDto(saved);
    }

    public WorkoutSetResponseDTO addSetToWorkoutExercise(Long exerciseId, WorkoutSetRequestDTO dto) {
        Long currentUserId = userService.getCurrentUser().getId();
        WorkoutExercise workoutExercise = workoutExerciseRepository.findByIdAndUserId(exerciseId, currentUserId)
                .orElseThrow(()-> new WorkoutExerciseDoesNotExistException("This workoutExercise does not exist"));

        WorkoutSet newSet = workoutSetMapper.toEntity(dto);
        workoutExercise.addWorkoutSet(newSet);

        workoutExerciseRepository.save(workoutExercise);

        return workoutSetMapper.toResponseDTO(newSet);
    }

    public void deleteSetFromWorkoutExercise(Long exerciseId, Long setId) {
        Long currentUserId = userService.getCurrentUser().getId();
        WorkoutExercise workoutExercise = workoutExerciseRepository.findByIdAndUserId(exerciseId, currentUserId)
                .orElseThrow(()->new WorkoutExerciseDoesNotExistException("This workoutExercise does not exist"));
        WorkoutSet set = workoutExercise.getWorkoutSetList().stream()
                .filter(s -> s.getId().equals(setId))
                .findFirst()
                .orElseThrow(() -> new SetDoesNotExistException("This set doesn't exist"));
        workoutExercise.deleteWorkoutSet(set);
        workoutExerciseRepository.save(workoutExercise);
    }
}
