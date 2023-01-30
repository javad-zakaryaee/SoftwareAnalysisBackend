package com.barfix.back.repository;


import com.barfix.back.model.Trainee;
import com.barfix.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, String> {
    Optional<Trainee> findByUser(User user);
}