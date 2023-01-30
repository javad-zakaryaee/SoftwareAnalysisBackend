package com.barfix.back.repository;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Trainee;
import com.barfix.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {
    Optional<Coach> findByUser(User user);
}