package com.barfix.back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PlanExerciseId implements Serializable {
    @Column(name = "plan_id")
    private String plan;
    @Column(name = "exercise_id")
    private String exercise;
}
