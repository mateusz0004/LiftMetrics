package com.liftmetrics.repository;

import com.liftmetrics.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SetsRepository extends JpaRepository<WorkoutSet,Long> {
    List<WorkoutSet> findByWorkoutExercise_Id(Long workoutExerciseId);
}
