package com.liftmetrics.repository;

import com.liftmetrics.dto.workoutExercise.WorkoutExerciseResponseDTO;
import com.liftmetrics.model.WorkoutExercise;
import com.liftmetrics.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    @Query("SELECT we FROM WorkoutExercise we " +
            "JOIN we.workoutSession ws " +
            "JOIN ws.workoutSchedule wsc " +
            "WHERE wsc.user.id = :userId " +
            "FETCH we.workoutSession")
    List<WorkoutExercise> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT we FROM WorkoutExercise we "
            + "JOIN we.workoutSession ws " +
            "JOIN ws.workoutSchedule wsc " +
            "WHERE wsc.user.id = :userId AND we.id = :exerciseId " +
            "FETCH we.workoutSession")
    Optional<WorkoutExercise> findByIdAndUserId(@Param("exerciseId") Long exerciseId, @Param("userId") Long userId);
}
