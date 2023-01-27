package com.barfix.back.controller;

import com.barfix.back.model.Coach;
import com.barfix.back.model.Exercise;
import com.barfix.back.model.Plan;
import com.barfix.back.model.PlanExercise;
import com.barfix.back.repository.CoachRepository;
import com.barfix.back.repository.ExerciseRepository;
import com.barfix.back.repository.PlanExerciseRepository;
import com.barfix.back.repository.PlanRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plan")
public class PlanController {
    @Autowired
    PlanRepository planRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    PlanExerciseRepository planExerciseRepository;
    @Autowired
    CoachRepository coachRepository;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity getAllPlan() {
//        List<Plan> planList = planRepository.findAll();
//        JSONObject response = new JSONObject();
//        planList.forEach(plan -> {
//            response.put("id", plan.getId());
//            response.put("name", plan.getName());
//            response.put("desc", plan.getText());
//            JSONArray exerciseArray = new JSONArray(plan.getExercises());
//            response.put("moves", exerciseArray);
//        });
//        return new ResponseEntity(response, HttpStatus.OK);
        return new ResponseEntity(planRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity createPlan(@RequestBody String attributes) {
        JSONObject json = new JSONObject(attributes);
        Optional<Coach> coach = coachRepository.findById(json.getString("coach"));
        Plan plan = new Plan(UUID.randomUUID().toString(), coach.get(), json.getString("name"), json.getString("desc"));
        planRepository.save(plan);
        Iterator<Object> exerciseList = json.getJSONArray("exercises").iterator();
        while (exerciseList.hasNext()) {
            JSONObject singleExercise = (JSONObject) exerciseList.next();
            Optional<Exercise> exercise = exerciseRepository.findById(singleExercise.getString("id"));
            PlanExercise planExercise = new PlanExercise(plan, exercise.get(), singleExercise.getInt("repeat"));
            planExerciseRepository.save(planExercise);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}

