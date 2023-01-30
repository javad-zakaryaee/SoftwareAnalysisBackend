package com.barfix.back.controller;

import com.barfix.back.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {
    @Autowired
    ExerciseRepository exerciseRepository;
    @GetMapping("/get/{id}")
    public ResponseEntity getExercise(@PathVariable String id){
        return new ResponseEntity( exerciseRepository.findById(id).get(), HttpStatus.OK);
    }
}
