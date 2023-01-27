package com.barfix.back.repository;


import com.barfix.back.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee, String> {

}