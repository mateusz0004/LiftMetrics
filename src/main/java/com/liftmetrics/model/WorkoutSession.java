package com.liftmetrics.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="workout_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSession {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExercise> workoutExercisesList;
    @ManyToOne
    @JoinColumn(name="workout_schedule_id")
    private WorkoutSchedule workoutSchedule;

    public void addWorkoutExercise(WorkoutExercise workoutExercise){
        workoutExercise.setWorkoutSession(this);
        workoutExercisesList.add(workoutExercise);
    }
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise){
        workoutExercise.setWorkoutSession(null);
        workoutExercisesList.remove(workoutExercise);
    }

}
