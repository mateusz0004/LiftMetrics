package com.liftmetrics.service;
import com.liftmetrics.dto.session.WorkoutSessionRequestDTO;
import com.liftmetrics.dto.session.WorkoutSessionResponseDTO;
import com.liftmetrics.dto.workoutExercise.WorkoutExerciseRequestDTO;
import com.liftmetrics.exception.workoutExercise.WorkoutExerciseDoesNotExistException;
import com.liftmetrics.exception.workoutSession.WorkoutSessionDoesNotExist;
import com.liftmetrics.mapper.WorkoutExerciseMapper;
import com.liftmetrics.mapper.WorkoutSessionMapper;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.liftmetrics.repository.WorkoutSessionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutSessionService {
    private WorkoutSessionRepository workoutSessionRepository;
    private WorkoutExerciseService workoutExerciseService;
    private WorkoutSessionMapper workoutSessionMapper;
    private WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, WorkoutExerciseService workoutExerciseService, WorkoutSessionMapper workoutSessionMapper) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.workoutExerciseService = workoutExerciseService;
        this.workoutSessionMapper = workoutSessionMapper;
    }

    public List<WorkoutSessionResponseDTO> findAll(){
        return workoutSessionRepository.findAll()
                .stream().
                map(workoutSessionMapper::toResponseDto)
                .collect(Collectors.toList());
    }
        public WorkoutSessionResponseDTO getWorkoutSessionById(Long id){
            return workoutSessionRepository.findById(id)
                    .map(workoutSessionMapper::toResponseDto)
                    .orElseThrow(()-> new WorkoutSessionDoesNotExist("This session doesn't exist"));
        }
    public WorkoutSessionResponseDTO addWorkoutSession(WorkoutSessionRequestDTO workoutSessionRequestDTO) {
        WorkoutSession session = workoutSessionMapper.toEntity(workoutSessionRequestDTO);

        if (session.getWorkoutExercisesList() != null) {
            for (WorkoutExercise exercise : session.getWorkoutExercisesList()) {
                session.addWorkoutExercise(exercise);
            }
        }

        WorkoutSession saved = workoutSessionRepository.save(session);
        return workoutSessionMapper.toResponseDto(saved);
    }
    public void deleteWorkoutSession(Long id){
        WorkoutSession workoutSession = workoutSessionRepository.findById(id)
                        .orElseThrow(()-> new WorkoutSessionDoesNotExist("This session doesn't exist"));
        workoutSessionRepository.delete(workoutSession);
    }
    public WorkoutSessionResponseDTO updateWorkoutSession(Long id, WorkoutSessionRequestDTO workoutSessionRequestDTO){
        WorkoutSession workoutSession = workoutSessionRepository.findById(id)
                .orElseThrow(()-> new WorkoutSessionDoesNotExist("This session doesn't exist"));

        workoutSession.setName(workoutSessionRequestDTO.getName());
        workoutSession.getWorkoutExercisesList().clear();

        WorkoutSession updated = workoutSessionRepository.save(workoutSession);

        return workoutSessionMapper.toResponseDto(updated);
    }
    public WorkoutSessionResponseDTO addExerciseToWorkoutSession(Long id , WorkoutExerciseRequestDTO workoutExerciseRequestDto){
        if(workoutExerciseRequestDto==null){
            throw new WorkoutExerciseDoesNotExistException("This workout exercise doesn't exist");
        }
        WorkoutSession workoutSession = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new WorkoutSessionDoesNotExist("Workout session does not exist"));
        WorkoutExercise workoutExercise = workoutExerciseMapper.toEntity(workoutExerciseRequestDto);
        workoutSession.addWorkoutExercise(workoutExercise);
        workoutSessionRepository.save(workoutSession);
        return workoutSessionMapper.toResponseDto(workoutSession);
    }
    public void deleteExerciseFromWorkoutSession(Long exerciseId, Long sessionId){
        WorkoutSession workoutSession = workoutSessionRepository.findById(sessionId)
                .orElseThrow(()-> new WorkoutSessionDoesNotExist("This workout session does not exist"));

        WorkoutExercise exerciseToDelete = workoutSession.getWorkoutExercisesList().stream()
                .filter(workoutExercise -> workoutExercise.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(()->new WorkoutExerciseDoesNotExistException("This workout exercise doesn't exist"));

        workoutSession.deleteWorkoutExercise(exerciseToDelete);
    }
}
