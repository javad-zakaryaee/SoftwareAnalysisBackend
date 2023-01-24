package com.barfix.back.repository;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, String> {

}