package com.liftmetrics.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "workout_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private double IWV;
    private double fatigue;
    private double exerciseTotalVolume;
    @OneToOne
    @JoinColumn(name = "workout_exercise_id")
    private WorkoutExercise workoutExercise;
}
