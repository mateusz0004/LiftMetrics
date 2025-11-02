package com.liftmetrics.service;
import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleRequestDTO;
import com.liftmetrics.dto.workoutSchedule.WorkoutScheduleResponseDTO;
import com.liftmetrics.exception.schedule.WorkoutScheduleDoesNotExist;
import com.liftmetrics.exception.workoutSession.WorkoutSessionDoesNotExist;
import com.liftmetrics.mapper.WorkoutScheduleMapper;
import com.liftmetrics.mapper.WorkoutSessionMapper;
import com.liftmetrics.model.User;
import com.liftmetrics.model.WorkoutSchedule;
import com.liftmetrics.model.WorkoutSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.WorkoutScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutScheduleService {
    private WorkoutSessionService workoutSessionService;
    private WorkoutScheduleRepository workoutScheduleRepository;
    private WorkoutScheduleMapper workoutScheduleMapper;
    private WorkoutSessionMapper workoutSessionMapper;

    public WorkoutScheduleService(WorkoutSessionService workoutSessionService, WorkoutScheduleRepository workoutScheduleRepository, WorkoutScheduleMapper workoutScheduleMapper, WorkoutSessionMapper workoutSessionMapper) {
        this.workoutSessionService = workoutSessionService;
        this.workoutScheduleRepository = workoutScheduleRepository;
        this.workoutScheduleMapper = workoutScheduleMapper;
        this.workoutSessionMapper = workoutSessionMapper;
    }

    public List<WorkoutScheduleResponseDTO> findAll (){
        return workoutScheduleRepository.findAll().stream()
                .map(workoutScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public WorkoutScheduleResponseDTO getWorkoutScheduleById(Long id){
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(id)
                .orElseThrow(()-> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));
        return workoutScheduleMapper.toResponseDto(workoutSchedule);
    }
    public WorkoutScheduleResponseDTO addWorkoutSchedule(WorkoutScheduleRequestDTO workoutScheduleRequestDTO){
        if(workoutScheduleRequestDTO==null){
            throw new WorkoutScheduleDoesNotExist("This workout schedule does not exist");
        }
        WorkoutSchedule workoutSchedule = workoutScheduleMapper.toEntity(workoutScheduleRequestDTO);
        WorkoutSchedule saved = workoutScheduleRepository.save(workoutSchedule);
        return workoutScheduleMapper.toResponseDto(saved);
    }
    public void deleteWorkoutSchedule(Long id){
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(id)
                .orElseThrow(() -> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));
        workoutScheduleRepository.delete(workoutSchedule);
    }
    public WorkoutScheduleResponseDTO updateWorkoutSchedule(Long id, WorkoutScheduleRequestDTO workoutScheduleRequestDTO){
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(id)
                .orElseThrow(() -> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));

        workoutSchedule.setName(workoutScheduleRequestDTO.getName());
        workoutSchedule.setType(workoutScheduleRequestDTO.getType());
        if (workoutScheduleRequestDTO.getUserId() != null) {
            User user = new User();
            user.setId(workoutScheduleRequestDTO.getUserId());
            workoutSchedule.setUser(user);
        }
        WorkoutSchedule saved = workoutScheduleRepository.save(workoutSchedule);
        return workoutScheduleMapper.toResponseDto(saved);
    }

    public WorkoutScheduleResponseDTO addSessionToWorkoutSchedule(Long id , WorkoutSessionRequestDTO workoutSessionRequestDTO){
        if(workoutSessionRequestDTO==null){
            throw new WorkoutSessionDoesNotExist("This workout session doesn't exist");
        }
        WorkoutSchedule workoutschedule = workoutScheduleRepository.findById(id)
                .orElseThrow(() -> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));
        WorkoutSession workoutSession = workoutSessionMapper.toEntity(workoutSessionRequestDTO);
        workoutschedule.addSession(workoutSession);
        WorkoutSchedule saved = workoutScheduleRepository.save(workoutschedule);
        return workoutScheduleMapper.toResponseDto(saved);
    }
    public void deleteSessionFromWorkoutSchedule(Long scheduleId, Long sessionId){
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new WorkoutScheduleDoesNotExist("This workout schedule does not exist"));

        WorkoutSession sessionToDelete = workoutSchedule.getWorkoutSessionList().stream()
                .filter(workoutSession -> workoutSession.getId().equals(sessionId))
                .findFirst()
                .orElseThrow(()->new WorkoutSessionDoesNotExist("This workout exercise doesn't exist"));

        workoutSchedule.deleteSession(sessionToDelete);
        workoutScheduleRepository.save(workoutSchedule);
    }
}
