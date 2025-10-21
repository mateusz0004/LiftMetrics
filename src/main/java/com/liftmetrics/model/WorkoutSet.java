package com.liftmetrics.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="workout_set")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSet {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Positive
    private double weight;
    @Positive
    private int reps;
    @Min(1)
    @Max(10)
    private int rpe;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id")
    @JsonBackReference
    private WorkoutExercise workoutExercise;
}
