package com.liftmetrics.service;
import com.liftmetrics.exception.workoutExercise.WorkoutExerciseDoesNotExistException;
import com.liftmetrics.exception.workoutSession.WorkoutSessionDoesNotExist;
import com.liftmetrics.exception.workoutSession.WorkoutExerciseListDoesNotExist;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.WorkoutSessionRepository;

import java.util.List;
@Service
@Transactional
public class WorkoutSessionService {
    private WorkoutSessionRepository workoutSessionRepository;
    private WorkoutExerciseService workoutExerciseService;

    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, WorkoutExerciseService workoutExerciseService) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.workoutExerciseService = workoutExerciseService;
    }

    public List<WorkoutSession> findAll(){
        return workoutSessionRepository.findAll();
    }
    public WorkoutSession getWorkoutSessionById(Long id){
        return workoutSessionRepository.findById(id)
                .orElseThrow(()-> new WorkoutSessionDoesNotExist("This session doesn't exist"));
    }
    public WorkoutSession addWorkoutSession(WorkoutSession workoutSession){
        if(workoutSession.getWorkoutExercisesList().isEmpty()){
            throw new WorkoutExerciseListDoesNotExist("This workout set list doesn't exist");
        }
        for(WorkoutExercise workoutExercise: workoutSession.getWorkoutExercisesList()){
            workoutSession.addWorkoutExercise(workoutExercise);
        }
        return workoutSessionRepository.save(workoutSession);
    }
    public void deleteWorkoutSession(Long id){
        WorkoutSession workoutSession = getWorkoutSessionById(id);
        workoutSessionRepository.delete(workoutSession);
    }
    public WorkoutSession updateWorkoutSession(Long id, WorkoutSession newWorkoutSession){
        WorkoutSession workoutSession = getWorkoutSessionById(id);

        workoutSession.setName(newWorkoutSession.getName());
        workoutSession.getWorkoutExercisesList().clear();

        for(WorkoutExercise actualExercise: newWorkoutSession.getWorkoutExercisesList()){
            workoutSession.addWorkoutExercise(actualExercise);
        }
        return workoutSessionRepository.save(workoutSession);
    }
    public WorkoutSession addExerciseToWorkoutSession(Long id ,WorkoutExercise workoutExercise){
        if(workoutExercise==null){
            throw new WorkoutExerciseDoesNotExistException("This workout exercise doesn't exist");
        }
        WorkoutSession workoutSession = getWorkoutSessionById(id);
        workoutSession.addWorkoutExercise(workoutExercise);
        return workoutSessionRepository.save(workoutSession);
    }
    public void deleteExerciseFromWorkoutSession(Long exerciseId, Long sessionId){
        WorkoutSession workoutSession = getWorkoutSessionById(sessionId);

        WorkoutExercise exerciseToDelete = workoutSession.getWorkoutExercisesList().stream()
                .filter(workoutExercise -> workoutExercise.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(()->new WorkoutExerciseDoesNotExistException("This workout exercise doesn't exist"));

        workoutSession.deleteWorkoutExercise(exerciseToDelete);
    }
}
