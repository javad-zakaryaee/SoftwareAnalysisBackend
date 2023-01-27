package com.barfix.back.repository;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Gym;
import com.barfix.back.model.Plan;
import com.barfix.back.model.PlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanExerciseRepository extends JpaRepository<PlanExercise, String> {

}