package com.liftmetrics.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="workout_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="exercise_id")
    private Exercise exercise;
    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WorkoutSet> workoutSetList;
    @Positive
    private int breakInSeconds;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_session_id")
    private WorkoutSession workoutSession;

    public void addWorkoutSet (WorkoutSet workoutSet){
        workoutSet.setWorkoutExercise(this);
        workoutSetList.add(workoutSet);
    }
    public void deleteWorkoutSet (WorkoutSet workoutSet){
        workoutSet.setWorkoutExercise(null);
        workoutSetList.remove(workoutSet);
    }
}
