package com.liftmetrics.service;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.dto.set.WorkoutSetRequestDTO;
import com.liftmetrics.dto.set.WorkoutSetResponseDTO;
import com.liftmetrics.exception.exercise.ExerciseDoesNotExist;
import com.liftmetrics.exception.set.SetDoesNotExistException;
import com.liftmetrics.exception.workoutExercise.ListOfSetsDoesNotExistException;
import com.liftmetrics.exception.workoutExercise.WorkoutExerciseDoesNotExistException;
import com.liftmetrics.mapper.WorkoutExerciseMapper;
import com.liftmetrics.mapper.WorkoutSetMapper;
import com.liftmetrics.model.Exercise;
import com.liftmetrics.model.WorkoutSet;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.SetsRepository;
import com.liftmetrics.repository.WorkoutExerciseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final SetsRepository setsRepository;
    private final WorkoutSetMapper workoutSetMapper;
    private final WorkoutExerciseMapper workoutExerciseMapper;
    private final ExerciseRepository exerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, SetsRepository setsRepository, WorkoutSetMapper workoutSetMapper, WorkoutExerciseMapper workoutExerciseMapper,
     ExerciseRepository exerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.setsRepository = setsRepository;
        this.workoutSetMapper=workoutSetMapper;
        this.workoutExerciseMapper=workoutExerciseMapper;
        this.exerciseRepository=exerciseRepository;
    }

    public List<WorkoutExerciseResponseDTO> findAll() {
        return workoutExerciseRepository.findAll()
                .stream()
                .map(workoutExerciseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public WorkoutExercise getWorkoutExerciseById(Long id) {
        return workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new WorkoutExerciseDoesNotExistException("This workout doesn't exist"));
    }

    public WorkoutExercise addExercise(WorkoutExercise workoutExercise) {
        if (workoutExercise.getWorkoutSetList()==null) {
            throw new ListOfSetsDoesNotExistException("Sets does not exist");
        }
        Optional<Exercise> existingExercise = exerciseRepository.findByName(workoutExercise.getExercise().getName());
        if (existingExercise.isEmpty()) {
            throw new ExerciseDoesNotExist(
                    "Exercise " + workoutExercise.getExercise().getName() + " does not exist in exercise palette"
            );
        }
        workoutExercise.setExercise(existingExercise.get());
        List<WorkoutSet> sets = new ArrayList<>(workoutExercise.getWorkoutSetList());
        workoutExercise.getWorkoutSetList().clear();
        for (WorkoutSet set : sets) {
            workoutExercise.addWorkoutSet(set);
        }
        return workoutExerciseRepository.save(workoutExercise);
    }

    public void deleteWorkoutExercise(Long exerciseId) {
        WorkoutExercise workoutExercise = getWorkoutExerciseById(exerciseId);
        workoutExerciseRepository.delete(workoutExercise);
    }

    public WorkoutExercise updateExercise(Long exerciseId, WorkoutExercise tempWorkoutExercise) {
        WorkoutExercise workoutExercise = getWorkoutExerciseById(exerciseId);
        workoutExercise.setExercise(tempWorkoutExercise.getExercise());
        workoutExercise.setWorkoutSession(tempWorkoutExercise.getWorkoutSession());
        workoutExercise.setBreakInSeconds(tempWorkoutExercise.getBreakInSeconds());
        workoutExercise.getWorkoutSetList().clear();

        for (WorkoutSet newSet : tempWorkoutExercise.getWorkoutSetList()) {
            workoutExercise.addWorkoutSet(newSet);
        }
        return workoutExerciseRepository.save(workoutExercise);
    }

public WorkoutSetResponseDTO addSetToWorkoutExercise(Long exerciseId, WorkoutSetRequestDTO dto) {
    WorkoutExercise workoutExercise = getWorkoutExerciseById(exerciseId);

    WorkoutSet newSet = workoutSetMapper.toEntity(dto);
    workoutExercise.addWorkoutSet(newSet);

    workoutExerciseRepository.save(workoutExercise);

    return workoutSetMapper.toResponseDTO(newSet);
}

    public void deleteSetFromWorkoutExercise(Long exerciseId, Long setId) {
        WorkoutExercise workoutExercise = getWorkoutExerciseById(exerciseId);
        WorkoutSet set = workoutExercise.getWorkoutSetList().stream()
                .filter(s -> s.getId().equals(setId))
                .findFirst()
                .orElseThrow(() -> new SetDoesNotExistException("This set doesn't exist"));
        workoutExercise.deleteWorkoutSet(set);
        workoutExerciseRepository.save(workoutExercise);
    }
}


/// ///// zrobic te mappery dla kazdej klasy (dopytać czata czy trzeba dla wszysstkich entity robic takie maperki)
/// ////// zrobic te relacje dla kazdej klasy
