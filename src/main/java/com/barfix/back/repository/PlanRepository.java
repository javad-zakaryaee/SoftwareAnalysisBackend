package com.barfix.back.repository;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Gym;
import com.barfix.back.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, String> {

}