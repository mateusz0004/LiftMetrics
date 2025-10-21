package com.liftmetrics.model;

import com.liftmetrics.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="workout_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Type type;
    @OneToMany(mappedBy="workoutSchedule", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<WorkoutSession> workoutSessionList;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public void addSession(WorkoutSession workoutSession){
        workoutSession.setWorkoutSchedule(this);
        workoutSessionList.add(workoutSession);
    }
    public void deleteSession(WorkoutSession workoutSession){
        workoutSession.setWorkoutSchedule(null);
        workoutSessionList.remove(workoutSession);
    }
}
