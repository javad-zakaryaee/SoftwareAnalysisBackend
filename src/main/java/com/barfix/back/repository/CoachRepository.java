package com.barfix.back.repository;

import com.barfix.back.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, String> {

}