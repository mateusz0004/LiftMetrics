package com.liftmetrics.repository;

import com.liftmetrics.model.WorkoutMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutMetricsRepository extends JpaRepository<WorkoutMetrics, Long> {
}
