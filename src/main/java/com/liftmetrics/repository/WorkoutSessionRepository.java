package com.liftmetrics.repository;

import com.liftmetrics.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {
    @Query("SELECT ws FROM WorkoutSession ws " +
            "JOIN ws.workoutSchedule wsc " +
            "WHERE ws.id = :sessionId AND wsc.user.id = :userId")
    Optional<WorkoutSession> findByIdAndUserId(
            @Param("sessionId") Long sessionId,
            @Param("userId") Long userId);
}
