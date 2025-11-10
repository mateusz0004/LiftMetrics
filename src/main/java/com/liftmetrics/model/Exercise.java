package com.liftmetrics.model;

import com.liftmetrics.enums.MuscleGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "MuscleGroup cannot be null!")
    private MuscleGroup muscleGroup;
}
