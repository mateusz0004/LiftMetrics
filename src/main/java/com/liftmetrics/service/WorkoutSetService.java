package com.liftmetrics.service;

import com.liftmetrics.exception.set.SetDoesNotExistException;
import com.liftmetrics.mapper.WorkoutSetMapper;
import com.liftmetrics.model.WorkoutSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.SetsRepository;

import java.util.List;

@Service
@Transactional
public class WorkoutSetService {
    private final SetsRepository setsRepository;
    private final WorkoutSetMapper workoutSetMapper;

    public WorkoutSetService(SetsRepository setsRepository, WorkoutSetMapper workoutSetMapper) {
        this.setsRepository = setsRepository;
        this.workoutSetMapper = workoutSetMapper;
    }

    public WorkoutSet addSet(WorkoutSet set) {
        return setsRepository.save(set);
    }

    public void deleteWorkoutSet(Long id) {
        WorkoutSet set = setsRepository.findById(id).
                orElseThrow(() -> new SetDoesNotExistException("Set not found by ID"));
        setsRepository.delete(set);
    }

    public List<WorkoutSet> getAllWorkoutSet() {
        return setsRepository.findAll();
    }

    public WorkoutSet getWorkoutSetById(Long id) {
        return setsRepository.findById(id).
                orElseThrow(() -> new SetDoesNotExistException("Set not found by id "));
    }

    public WorkoutSet updateWorkoutSet(Long id, WorkoutSet tempSet) {
        WorkoutSet set = setsRepository.findById(id)
                .orElseThrow(() -> new SetDoesNotExistException("Set not found by this id"));
        set.setWeight(tempSet.getWeight());
        set.setReps(tempSet.getReps());
        set.setRpe(tempSet.getRpe());
        return setsRepository.save(set);
    }

    public double calculateRIR(Long id) {
        WorkoutSet tempSet = setsRepository.findById(id).orElseThrow(() -> new SetDoesNotExistException("Set not found by ID"));
        return 10 - tempSet.getRpe();
    }
}

