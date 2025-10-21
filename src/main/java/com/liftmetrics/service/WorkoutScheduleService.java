package com.liftmetrics.service;
import com.liftmetrics.exception.schedule.WorkoutScheduleDoesNotExist;
import com.liftmetrics.exception.workoutSession.WorkoutSessionDoesNotExist;
import com.liftmetrics.model.WorkoutSchedule;
import com.liftmetrics.model.WorkoutSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.WorkoutScheduleRepository;

import java.util.List;

@Service
@Transactional
public class WorkoutScheduleService {
    private WorkoutSessionService workoutSessionService;
    private WorkoutScheduleRepository workoutScheduleRepository;

    public WorkoutScheduleService(WorkoutSessionService workoutSessionService, WorkoutScheduleRepository workoutScheduleRepository) {
        this.workoutSessionService = workoutSessionService;
        this.workoutScheduleRepository = workoutScheduleRepository;
    }

    public List<WorkoutSchedule> findAll (){
        return workoutScheduleRepository.findAll();
    }
    public WorkoutSchedule getWorkoutScheduleById(Long id){
        return workoutScheduleRepository.findById(id)
                .orElseThrow(()-> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));
    }
    public WorkoutSchedule addWorkoutSchedule(WorkoutSchedule workoutSchedule){
        if(workoutSchedule==null){
            throw new WorkoutScheduleDoesNotExist("This workout schedule does not exist");
        }
        for(WorkoutSession workoutSession: workoutSchedule.getWorkoutSessionList()){
            workoutSchedule.addSession(workoutSession);
        }
        return workoutScheduleRepository.save(workoutSchedule);
    }
    public void deleteWorkoutSchedule(Long id){
        WorkoutSchedule workoutSchedule = getWorkoutScheduleById(id);
        workoutScheduleRepository.delete(workoutSchedule);
    }
    public WorkoutSchedule updateWorkoutSchedule(Long id, WorkoutSchedule newWorkoutSchedule){
        WorkoutSchedule workoutSchedule = getWorkoutScheduleById(id);

        workoutSchedule.setName(newWorkoutSchedule.getName());
        workoutSchedule.setType(newWorkoutSchedule.getType());
        workoutSchedule.setUser(newWorkoutSchedule.getUser());
        workoutSchedule.getWorkoutSessionList().clear();

        for(WorkoutSession actualWorkoutSession: newWorkoutSchedule.getWorkoutSessionList()){
            workoutSchedule.addSession(actualWorkoutSession);
        }
        return workoutScheduleRepository.save(workoutSchedule);
    }
    public WorkoutSchedule addSessionToWorkoutSchedule(Long id ,WorkoutSession workoutSession){
        if(workoutSession==null){
            throw new WorkoutSessionDoesNotExist("This workout session doesn't exist");
        }
        WorkoutSchedule workoutschedule = getWorkoutScheduleById(id);
        workoutschedule.addSession(workoutSession);
        return workoutScheduleRepository.save(workoutschedule);
    }
    public void deleteSessionFromWorkoutSchedule(Long scheduleId, Long sessionId){
        WorkoutSchedule workoutSchedule = getWorkoutScheduleById(scheduleId);

        WorkoutSession sessionToDelete = workoutSchedule.getWorkoutSessionList().stream()
                .filter(workoutSession -> workoutSession.getId().equals(sessionId))
                .findFirst()
                .orElseThrow(()->new WorkoutSessionDoesNotExist("This workout exercise doesn't exist"));

        workoutSchedule.deleteSession(sessionToDelete);
    }
}
