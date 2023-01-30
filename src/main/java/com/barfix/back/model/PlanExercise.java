package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "PlanExercise")
@Table(name = "plan_exercise", schema = "public")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PlanExercise {
    @EmbeddedId
    private PlanExerciseId id = new PlanExerciseId();


    @ManyToOne
    @MapsId("planId")
    @NonNull
    @JsonBackReference
    private Plan plan;


    @ManyToOne
    @MapsId("exerciseId")
    @NonNull
    @JsonBackReference
    private Exercise exercise;

    @Column(name = "repeat")
    @NonNull
    private Integer repeat;
}
