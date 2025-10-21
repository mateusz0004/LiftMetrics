package com.liftmetrics.repository;

import com.liftmetrics.model.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
}
