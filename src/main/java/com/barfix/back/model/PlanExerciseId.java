package com.barfix.back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PlanExerciseId implements Serializable {
    @NonNull
   private String planId;
    @NonNull
   private String exerciseId;
}
